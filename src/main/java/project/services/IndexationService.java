package project.services;


import dto.ResultIndexing;
import dto.ResultStatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.LemCreator;
import project.Main;
import project.model.Index;
import project.model.Lemma;
import project.model.Page;
import project.model.Site;

import javax.persistence.NonUniqueResultException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс построничной индексации сайта
 *
 * @author VG
 * @version 0.1
 **/
@Scope ("prototype")
@Service
@RequiredArgsConstructor
public class IndexationService {
    public LemCreator lemCreator = new LemCreator();
    public final PageService pageService;
    public final LemmaService lemmaServise;
    public final IndexService indexService;
    public final PageCreatorService pageCreatorService;
    public final SiteConnectService siteConnection;
    public final SiteService siteService;
    public static String lastIndexingError;
    public static Long statusTime;
    private Logger logger = LogManager.getLogger(IndexationService.class);


    /**
     * Функция индексации страницы по адресу
     *
     * @param pageUrl - Адресс страницы в установленном формате
     **/
    public ResponseEntity<ResultStatisticDto> indexPageRequest (String pageUrl) {
        ResultStatisticDto dto = new ResultStatisticDto();
        if (!isInSitesList(pageUrl)) {
            unAvailableSiteMessage();
        }
        pageCreatorService.createPage(pageUrl);
        indexPage(pageUrl);
        dto.setResult("true");
        logger.log(Level.valueOf("INFO"), "проиндесирована страница: " + pageUrl);
        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    public ResultIndexing indexPage (String pageUrl) {

        Page page = pageCreatorService.createPage(pageUrl);
        if (!isInSitesList(pageUrl)) {
            unAvailableSiteMessage();
        }
//        String correctUrl = getUrlWithoutSiteMain(pageUrl);
//        Integer site_id = getSiteId(pageUrl);
//        try {
//            page = pageService.getPage(correctUrl, site_id);
//        } catch (NonUniqueResultException ex) {
//            logger.log(Level.INFO, "Страница Уже проиндексирована");
//        }
        HashMap<String, Integer> titleLemma;
        HashMap<String, Integer> bodyLemma;
        titleLemma = lemCreator.getLem(getContentWithSelector(page.getContent(), "title"));
        bodyLemma = lemCreator.getLem(getContentWithSelector(page.getContent(), "body"));
        Map<String, Float> rank = calculateLemmaRank(titleLemma, bodyLemma);
        for (Map.Entry<String, Integer> entry : titleLemma.entrySet()) {
            if (bodyLemma.containsKey(entry.getKey())) {
                bodyLemma.put(entry.getKey(), entry.getValue() + bodyLemma.get(entry.getKey()));
            }
        }
        for (Map.Entry<String, Integer> entry : bodyLemma.entrySet()) {
            Index index = indexCreator(page, entry.getKey(), entry.getValue());
            index.setRank(rank.get(entry.getKey()));
            indexService.save(index);

            updateSiteInform(lastIndexingError, new Date(), page.getSiteId());
        }
        titleLemma.clear();
        bodyLemma.clear();
        logger.log(Level.INFO, "Страница проиндексирована: " + pageUrl);
        return ResultIndexing.pageIndexationTrue();

    }

    public Index indexCreator (Page page, String lemmaName, Integer lemmaFrequency) {

        Index index = new Index();
        Lemma lemma = lemmaServise.getLemmaByName(lemmaName);

        if (lemma != null) {
            lemma.setFrequency(lemma.getFrequency() + lemmaFrequency);
            lemmaServise.updateLemma(lemma);
            index.setLemmaId(lemma.getId());
        } else {
            lemma = new Lemma();
            lemma.setLemma(lemmaName);
            lemma.setFrequency(1);
            lemma.setSiteid(page.getSiteId());
            lemmaServise.addLemma(lemma);
            index.setLemmaId((lemmaServise.getLemmaByName(lemmaName)).getId());
        }
        index.setSiteId(page.getSiteId());
        index.setPageId(page.getId());
        return index;
    }

    /**
     * Функция расчета ранга леммы
     *
     * @param bodyLemma  - Леммы тела сайта
     * @param titleLemma - леммы заголовка сайта
     * @return Возвращает список лемм с рачитанными рангами
     */
    public static Map<String, Float> calculateLemmaRank (Map<String, Integer> titleLemma, Map<String, Integer> bodyLemma) {
        float fullRank;
        float tittleRank;
        float bodyRank;
        Map<String, Float> lemmaRank = new HashMap<>();
        for (Map.Entry<String, Integer> entry : bodyLemma.entrySet()) {
            if (titleLemma.containsKey(entry.getKey())) {
                tittleRank = titleLemma.get(entry.getKey());
                bodyRank = 0.8F * entry.getValue();
                fullRank = tittleRank + bodyRank;
                lemmaRank.put(entry.getKey(), fullRank);
            } else {
                fullRank = 1 + 0.8f * entry.getValue();
                lemmaRank.put(entry.getKey(), fullRank);
            }
        }
        return lemmaRank;
    }

//    public String getUrlWithoutSiteMain (String url) {
//        String[] split = url.split("/");
//        StringBuilder correctUrl = new StringBuilder();
//        if (split.length <= 3) {
//            return "/";
//        }
//        for (int i = 3; i < split.length; i++) {
//            correctUrl.append("/").append(split[i]);
//        }
//        return String.valueOf(correctUrl);
//    }

    public boolean isInSitesList (String url) {
        String siteUrl = getSiteTitleUrl(url);
        List<Site> siteList = Main.availableSites;
        for (Site st : siteList) {
            if (st.getUrl().contains(siteUrl)) {
                return true;
            }
        }
        return false;
    }

    public String getSiteTitleUrl (String url) {
        String[] urlSplit = url.split("/");
        return urlSplit[0] + "//" + urlSplit[1] + urlSplit[2];
    }

//    public Integer getSiteId (String url) {
//        String siteUrl = getSiteTitleUrl(url);
//        List<Site> siteList = Main.availableSites;
//        for (Site st : siteList) {
//            if (st.getUrl().contains(siteUrl)) {
//                return st.getId();
//            }
//        }
//        return 0;
//    }

    public String getContentWithSelector (String text, String selector) {
        Element content = Jsoup.parse(text);
        return content.select(selector).toString();
    }

    public void updateSiteInform (String error, Date statusTime, Integer siteId) {
        siteService.updateSiteErrorAndStatusTime(error, statusTime, siteId);
    }
    public ResponseEntity<ResultIndexing> unAvailableSiteMessage(){
        return ResponseEntity.ok(ResultIndexing.pageIndexationFalse());
    }


}


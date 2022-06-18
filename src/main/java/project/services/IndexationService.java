package project.services;


import dto.ResultIndexing;
import dto.ResultStatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.LemCreator;
import project.model.Index;
import project.model.Lemma;
import project.model.Page;
import project.model.Site;
import project.repositoryes.SiteConnection;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * Класс построничной индексации сайта
 *
 * @author VG
 * @version 0.1
 **/
@Service
@RequiredArgsConstructor
public class IndexationService {
    public final LemCreator lemCreator = new LemCreator();
    public final SiteService siteService;
    public final PageServise pageServise;
    public final LemmaServise lemmaServise;
    public final IndexService indexService;
    public final PageCreatorService pageCreatorService;
    public final SiteConnection siteConnection;
    public final MapperService mapperService;


    /**
     * Функция индексации страницы по адресу
     *
     * @param pageUrl - Адресс страницы в установленном формате
     **/
    public ResponseEntity<ResultStatisticDto> indexPageRequest(String pageUrl){
        ResultStatisticDto dto = new ResultStatisticDto();
        if(isInSearchField(pageUrl)){
            pageCreatorService.createPage(pageUrl);
            indexPage(pageUrl);
            dto.setResult("true");
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.ok(ResultStatisticDto.getFalseIndexPageResult());
    }
    @SneakyThrows
    public void indexPage (String pageUrl) {
        Document document = siteConnection.getConnection(pageUrl).parse();
        Map<String, Integer> titleLemma;
        Map<String, Integer> bodyLemma;
        String correctUrl = getCorrectUrl(pageUrl);
        Integer site_id = getSiteId(pageUrl);
        Page page = pageServise.getPage(correctUrl, site_id);
        titleLemma = lemCreator.getLem(siteConnection.getContentWithSelector("title", document));
        bodyLemma = lemCreator.getLem(siteConnection.getContentWithSelector("body", document));
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
        }
        titleLemma.clear();
        bodyLemma.clear();
        System.out.println("страница проиндексирована");

    }

    public Index indexCreator (Page page, String entry, Integer value) {

        Index index = new Index();
        Lemma lemma = new Lemma();

        lemma = lemmaServise.getLemmaByName(entry);
        if (lemma != null) {
            lemma.setFrequency(lemma.getFrequency() + value);
            lemmaServise.updateLemma(lemma);
            index.setLemmaid(lemma.getId());
        } else {
            lemma = new Lemma();
            lemma.setLemma(entry);
            lemma.setFrequency(1);
            lemma.setSiteid(page.getId());
            lemmaServise.addLemma(lemma);
            index.setLemmaid((lemmaServise.getLemmaByName(entry)).getId());
        }
        index.setPageid(page.getId());
        return index;
    }

    /**
     * Функция расчета ранга леммы
     *
     * @param bodyLemm  - Леммы тела сайта
     * @param titleLemm - леммы заголовка сайта
     * @return Возвращает список лемм с рачитанными рангами
     */
    public static Map<String, Float> calculateLemmaRank (Map<String, Integer> titleLemm, Map<String, Integer> bodyLemm) {
        float rank;
        float tittleRank;
        float bodyRank;
        Map<String, Float> lemmaRank = new HashMap<>();
        for (Map.Entry<String, Integer> entry : bodyLemm.entrySet()) {
            if (titleLemm.containsKey(entry.getKey())) {
                tittleRank = titleLemm.get(entry.getKey());
                bodyRank = 0.8F * entry.getValue();
                rank = tittleRank + bodyRank;
                lemmaRank.put(entry.getKey(), rank);
            } else {
                rank = 1 + 0.8f * entry.getValue();
                lemmaRank.put(entry.getKey(), rank);
            }
        }
        return lemmaRank;
    }

    public String getCorrectUrl (String url) {
        String[] split = url.split("/");
        StringBuilder correctUrl = new StringBuilder();
        if (split.length <= 3) {
            return "/";
        }
        for (int i = 3; i < split.length; i++) {
            correctUrl.append("/").append(split[i]);
        }
        return String.valueOf(correctUrl);
    }

    public boolean isInSearchField (String url) {
        String siteUrl = getSiteTitleUrl(url);
        List<Site> siteList = siteService.getAllSites();
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

    public Integer getSiteId (String url) {
        String siteUrl  = getSiteTitleUrl(url);
        List<Site> siteList = siteService.getAllSites();
        for (Site st : siteList) {
            if (st.getUrl().contains(siteUrl)) {
                return st.getId();
            }
        }
        return 0;
    }
    public ResultIndexing startTotalIndexing(){
        List<Site> sites = siteService.getAllSites();
        List<MapperService> sitesForIndexing = new ArrayList<>();
        for(Site st: sites){
            mapperService.
            MapperService indexingSiteToList = new MapperService(new Nodelink(st.getUrl()));
            sitesForIndexing.add(indexingSiteToList);
        }
        invoke(sitesForIndexing);
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        resultIndexing.setError("nonError");
        return resultIndexing;
    }
    public void invoke(List<MapperService> siteMappers){
        for(MapperService siteMapper: siteMappers){
            new ForkJoinPool(2, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true).invoke(siteMapper);
        }
    }


}


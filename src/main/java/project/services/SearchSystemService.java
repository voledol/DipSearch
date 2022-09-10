package project.services;


import dto.ResultPageDto;
import dto.ResultSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import project.LemCreator;
import project.Main;
import project.model.*;

import java.util.*;

/**
 * @author VG
 * @version 0.1
 */
@Service
@RequiredArgsConstructor
public class SearchSystemService {
    public final PageService pageServise;
    public final LemmaService lemmaServise;
    public final IndexService indexService;
    public final SiteService siteService;
    public final Logger searchSystemLogger = LogManager.getLogger(SearchSystemService.class);
    private LemCreator lemCreator = new LemCreator();
    private String searchRequest;
    private Integer offset;
    private Integer limit;
    private String site;


    @SneakyThrows
    public ResponseEntity<ResultSearchDto> find (String searchRequest, String site, String stringOffset, String stringLimit) {
        this.searchRequest = searchRequest;
        this.offset = Integer.parseInt(stringOffset);
        this.limit = Integer.parseInt(stringLimit);
        this.site = site;
        HashMap<String, Integer> lemmasOfSearchRequest = new HashMap<>();
        List<Lemma> lemmasFromDB = new ArrayList<>();
        lemmasOfSearchRequest = lemCreator.getLem(searchRequest);
        lemmasFromDB = lemmaServise.findLemmaList(lemmasOfSearchRequest);
        lemmasFromDB = requestCorrection(lemmasFromDB);
        if (lemmasFromDB.size() == 0) {
            return badRequest();
        }
        List<Index> finalInd = findIndexListByFirstLemma(lemmasFromDB.get(0));

        for (int i = 1; i < lemmasFromDB.size(); i++) {
            finalInd = removePages(lemmasFromDB.get(i), finalInd);
        }
        List<Page> resultsPage = getResultList(finalInd);
        List<ResultPageDto> resultPageDtoList = moveToResultPage(getRelevance(resultsPage, lemmasFromDB));
        resultPageDtoList = subListByLimit(resultPageDtoList, limit);
        System.out.println(resultPageDtoList);
        if (resultsPage.isEmpty()) {
            return badRequest();
        }
        System.out.println(resultPageDtoList);
        return successRequest(resultPageDtoList);
    }

    public List<Lemma> requestCorrection (List<Lemma> lemmaListForCorrection) {
        lemmaListForCorrection.removeIf(Objects::isNull);
        return lemmaListForCorrection;
    }

    public List<Index> findIndexListByFirstLemma (Lemma lemma) {
        List<Index> indexListByFirstLemma = new ArrayList<>();
        if (site != null) {
            int siteId = 0;
            for (Site siteObject : Main.availableSites) {
                if (siteObject.getUrl().equals(site)) {
                    siteId = siteObject.getId();
                }
            }
            indexListByFirstLemma = indexService.findIndexListByLemmaIdAndSiteId(lemma.id, siteId);
        } else {
            indexListByFirstLemma = indexService.findIndexListByLemmaId(lemma.id);
        }
        return indexListByFirstLemma;
    }

    @SneakyThrows
    public List<Page> getResultList (List<Index> ind) {
        long start = System.currentTimeMillis();
        List<Page> results = new ArrayList<>();
        for (Index index : ind) {
            Page page = pageServise.getPageById(index.getPageId()).get();
            results.add(page);
        }
        searchSystemLogger.log(Level.INFO, "получение финального списка старниц: " + (System.currentTimeMillis() - start) + "  " + results.size());
        return results;
    }

    public List<Index> removePages (Lemma lem, List<Index> ind) {
        long start = System.currentTimeMillis();
        List<Index> finPages = new ArrayList<>();
        for (Index index : ind) {
            Index pagesId = indexService.removePage(index.pageId, lem.getId());
            if (pagesId != null) {
                finPages.add(index);
            }
        }
        searchSystemLogger.log(Level.INFO, "отсев страниц: " + (System.currentTimeMillis() - start));
        return finPages;
    }


    public List<PageWithRelevance> getRelevance (List<Page> pages, List<Lemma> lemmas) {
        long start = System.currentTimeMillis();
        List<PageWithRelevance> pageWithRelevances = new ArrayList<>();
        for (Page page_id : pages) {
            double rank = 0.0;
            PageWithRelevance pageWithRelevance = new PageWithRelevance();
            for (Lemma lemma : lemmas) {
                Index index = indexService.findIndexByPage_idAndLemm_id(page_id.getId(), lemma.getId());
                rank += index.rank;
            }
            pageWithRelevance.setPage(page_id);
            pageWithRelevance.setRelevance(rank);
            pageWithRelevances.add(pageWithRelevance);
        }
        Collections.sort(pageWithRelevances);
        searchSystemLogger.log(Level.INFO, "расчет релевантности страницы: " + (System.currentTimeMillis() - start));

        return pageWithRelevances;
    }

    public List<ResultPageDto> moveToResultPage (List<PageWithRelevance> page) {

        List<ResultPageDto> result = new ArrayList<>();
        long startMoveToResult = System.currentTimeMillis();
        for (PageWithRelevance pageToMove : page) {
            if (result.size() == 10) {
                continue;
            }
            ResultPageDto resultPage = new ResultPageDto();
            String siteName = getSiteName(pageToMove.getPage());
            String siteUrl = getSiteUrl(pageToMove.getPage());
            resultPage.setRelevance(pageToMove.relevance);
            resultPage.setSiteName(siteName);
            resultPage.setSite(siteUrl.trim());
            resultPage.setUri(pageToMove.page.getPath());
            resultPage.setTitle(Jsoup.parse(pageToMove.page.getContent()).select("title").toString()
                    .replaceAll("<title>", "").replaceAll("</title>", ""));
            resultPage.setSnippet(getSnippet(pageToMove.getPage().getContent(), searchRequest));
            result.add(resultPage);
        }
        searchSystemLogger.log(Level.INFO, "преобразование страниц в ответ: " + (System.currentTimeMillis() - startMoveToResult));
        return result;
    }


    public String getSnippet (String content, String searchRequest) {
        String[] wordList = searchRequest.split("\\s");
        StringBuilder snippetToString = new StringBuilder();
        Integer start = content.toLowerCase(Locale.ROOT).indexOf(wordList[0].toLowerCase(Locale.ROOT));
        if (start != -1) {
            char[] snippet = new char[50];
            content.getChars(start, start + 50, snippet, 0);
            for (char ch : snippet) {
                snippetToString.append(ch);
            }
            snippetToString = new StringBuilder("<b>" + snippetToString + "</b>");
        } else {
            snippetToString = new StringBuilder("сниппет не найден");
        }
        return snippetToString.toString();
    }


    public String getSiteName (Page page) {
        return siteService.getSiteById(page.getSiteId()).get().getName();
    }

    public String getSiteUrl (Page page) {
        return siteService.getSiteById(page.getSiteId()).get().getUrl();
    }

    public ResponseEntity<ResultSearchDto> badRequest () {
        ResultSearchDto result = new ResultSearchDto();
        result.setResult("false");
        result.setError("ОШИБКА: На заданном сайте ничего не найдено, или задан пустой поисковой запрос");
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<ResultSearchDto> successRequest (List<ResultPageDto> resultPageDtoList) {
        ResultSearchDto result = new ResultSearchDto();
        result.setResult("true");
        result.setCount(String.valueOf(resultPageDtoList.size()));
        result.setData(resultPageDtoList);
        return ResponseEntity.ok(result);
    }

    public List<ResultPageDto> subListByLimit (List<ResultPageDto> result, Integer limit) {
        if (result.size() > limit) {
            result = result.subList(0, limit);
            return result;
        } else {
            return result;
        }
    }
}

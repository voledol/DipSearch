package project.services;


import dto.ResultPageDto;
import dto.ResultSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.LemCreator;
import project.model.Index;
import project.model.Lemma;
import project.model.Page;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author VG
 * @version 0.1
 */
@Service
@RequiredArgsConstructor
public class SearchSystemService {
    public final PageService pageServise;
    public final LemmaServise lemmaServise;
    public final IndexService indexService;
    public final SiteService siteService;
    public final SiteConnectService siteConnectService;
    private LemCreator lemCreator = new LemCreator();
    private String searchRequest;
    private int offset;
    private String site;

    @SneakyThrows
    public ResponseEntity<ResultSearchDto> find (String searchRequest, String site, String offset, String limit) {
        this.searchRequest = searchRequest;
        this.offset = Integer.parseInt(offset);
        this.site = site;
        HashMap<String, Integer> lemmasOfSearchRequest = new HashMap<>();
        List<Lemma> lemmasFromDB = new ArrayList<>(); //// продолжай допиливать поиск
        lemmasOfSearchRequest = lemCreator.getLem(searchRequest);
        lemmasFromDB = lemmaServise.findLemmaList(lemmasOfSearchRequest);
        Collections.sort(lemmasFromDB);
        List<Index> finalInd = indexService.findIndexListByLemmaId(lemmasFromDB.get(0).getId());
        for (int i = 1; i < lemmasFromDB.size(); i++) {
            finalInd = removePages(lemmasFromDB.get(i), finalInd);
        }
        ResultSearchDto result = new ResultSearchDto();
        List<Page> resultsPage = getResultList(finalInd, lemmasFromDB);
        List<Page> resNoDup = resultsPage.stream().distinct().collect(Collectors.toList());
        List<ResultPageDto> resultPageDtos = moveToResultPage(resNoDup, lemmasFromDB);
        if(resultPageDtos.size() > Integer.parseInt(limit)){
          resultPageDtos = resultPageDtos.subList(0, 10);
        }
        if (resultsPage.isEmpty()) {
            result.setResult("false");
            result.setError("error задан пустой поисковой запрос, или страница не найдена");
            return ResponseEntity.ok(result);
        }
        result.setResult("true");
        result.setCount("" + resultPageDtos.size());
        result.setData(resultPageDtos);
        return ResponseEntity.ok(result);
    }

    public Double getRelevance (int page_id, List<Lemma> lems) {// оптимизировать расчет релевантности
        long start = System.currentTimeMillis();
        double rank = 0.0;
        for (Lemma lemma : lems) {
            Index index = indexService.findIndexByPage_idAndLemm_id(page_id, lemma.getId());
            rank += index.rank;
        }
        System.out.println((System.currentTimeMillis()-start) + " расчет релевантности");
        return rank;
    }

    @SneakyThrows
    public List<Page> getResultList (List<Index> ind, List<Lemma> lemmasFromDB) {
        long start = System.currentTimeMillis();
        List<Page> results = new ArrayList<>();
        for (Index index : ind) {
            Page page = pageServise.getPageById(index.getPageid()).get();
            results.add(page);
        }
        System.out.println((System.currentTimeMillis()-start) + "получение финального списка старниц");
        return results;
    }

    public List<Index> removePages (Lemma lem, List<Index> ind) { // проверить правильность работы
        List<Index> finPages = new ArrayList<>();
        for (Index index : ind) {
            List<Index> pagesId = indexService.removePage(index.pageid, lem.getId());
            if(pagesId!=null){
                finPages.add(index);
            }
            }
            return finPages;
        }

    public String getSnippet (String content, String searchRequest) {
        String[] wordList = searchRequest.split(" ");
        StringBuilder snippetToString = new StringBuilder();
        Integer start = content.toLowerCase(Locale.ROOT).indexOf(wordList[0]);
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

    public List<ResultPageDto> moveToResultPage (List<Page> pages, List<Lemma> lemmasFromDB) {

        List<ResultPageDto> result = new ArrayList<>();
        for (Page page : pages) {
            long start = System.currentTimeMillis();
            ResultPageDto resultPage = new ResultPageDto();
            String siteName = getSiteName(page);
            String siteUrl = getSiteUrl(page);
            resultPage.setRelevance(getRelevance(page.getId(), lemmasFromDB));
                resultPage.setSiteName(siteName);
                resultPage.setSite(siteUrl.trim());
                resultPage.setUrl(page.getPath());
            System.out.println(System.currentTimeMillis()-start + " без запроса в интернет");
            long start1 = System.currentTimeMillis();
                try {
                    resultPage.setTitle(siteConnectService.getConnection(siteUrl + page.getPath())
                            .parse()
                            .select("title").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    resultPage.setTitle("нет сети");
                }
            System.out.println(System.currentTimeMillis() - start1 + "запрос в интернет");
                resultPage.setSnippet(getSnippet(page.getContent(), searchRequest));
                result.add(resultPage);
            }

        Collections.sort(result);
        return result;
    }
}

package project.services;


import dto.ResultPageDto;
import dto.ResultSearchDto;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.LemCreator;
import lombok.RequiredArgsConstructor;
import project.model.Index;
import project.model.Lemma;
import project.model.Page;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author VG
 * @version 0.1
 */
@Service
@RequiredArgsConstructor
public class SearchSystemService {
    public final PageServise pageServise;
    public final LemmaServise lemmaServise;
    public final IndexService indexService;
    public final SiteService siteService;
    public final SiteConnectService siteConnectService;
    private LemCreator lemCreator = new LemCreator();

    public ResponseEntity<ResultSearchDto> find(String searchRequest, String site, String offset, String limit){

        HashMap<String, Integer> lemmsOfSearchRequest = new HashMap<>();
        List<Lemma> lemmsFromDB = new ArrayList<>();
        lemmsOfSearchRequest = lemCreator.getLem(searchRequest);
        lemmsFromDB = lemmaServise.findLemmaList(lemmsOfSearchRequest);
        Collections.sort(lemmsFromDB);
        List<Index> ind = indexService.findIndexListByLemmaId(lemmsFromDB.get(0).getId());
        for(int i =1; i < lemmsFromDB.size();i++){
            ind = removePages(lemmsFromDB.get(i), ind);
        }
        ResultSearchDto result = new ResultSearchDto();
        List<ResultPageDto> results = getResultList(ind, searchRequest,lemmsFromDB);
        if(results.isEmpty()){
            result.setResult("false");
            result.setError("error задан пустой поисковой запрос");
            return ResponseEntity.ok(result);
        }
        result.setResult("true");
        result.setCount("" + results.size());

        result.setData(results);
        return ResponseEntity.ok(result);
    }
    public  Double relevance(int page_id, List<Lemma> lems){
        Double rank = 0.0;
        for(Lemma lemma: lems){
            Index index = indexService.findIndexByPage_idAndLemm_id(page_id, lemma.getId());
            rank += index.rank;
        }
        return rank;
    }
    //тут мне не нравится
    @SneakyThrows
    public List<ResultPageDto>getResultList(List<Index> ind, String searchRequest, List<Lemma> lemmsFromDB){
        List<ResultPageDto> results = new ArrayList<>();
        for (Index index: ind){
            ResultPageDto resultPage = new ResultPageDto();
            Page page = pageServise.getPageById(index.getPageid()).get();
            String siteName = getSiteName(page);
            String siteUrl = getSiteUrl(page);
            resultPage.setSiteName(siteName);
            resultPage.setSite(siteUrl);
            resultPage.setUrl(page.getPath());
            resultPage.setTitle(siteConnectService.getConnection(siteUrl + page.getPath())
                    .parse()
                    .select("title").toString());
            resultPage.setSnippet(getSnippet(page.getContent(), searchRequest));
            resultPage.setRelevance(relevance(page.getId(), lemmsFromDB));
            results.add(resultPage);

        }
        return results;
    }
    public  List<Index> removePages(Lemma lem, List<Index> ind){
        List<Index> finPages = new ArrayList<>();
        for(Index index: ind){
            List<Index> pagesId = indexService.findIndexListByPageId(index.getPageid());
            for(Index page: pagesId){
                if(page.getLemmaid()!= lem.getId()){
                    continue;
                }
                else{
                    finPages.add(page);
                }
            }
        }
        return finPages;

    }
    public String getSnippet(String content, String searchRequest) {
        String snippet = "";
        String[] wordList = searchRequest.split(" ");
        String regString = "(("+wordList[0]+")([\\s\\S]+?)("+wordList[wordList.length -1]+"))";
        Pattern pattern = Pattern.compile(regString);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            snippet = "<b> " + matcher.group().toString() + " </b>";
        }
        else{
            snippet = "snippet не найден";
        }

        return snippet;
    }
    public String getSiteName(Page page){
        return siteService.getSiteById(page.getSiteId()).get().getName();
    }
    public String getSiteUrl(Page page){
        return siteService.getSiteById(page.getSiteId()).get().getUrl();
    }
}

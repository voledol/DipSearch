package project.controllers;


import org.springframework.stereotype.Controller;
import project.services.IndexServise;
import project.services.LemmaServise;
import project.services.PageServise;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import project.model.Index;
import project.model.Lemma;
import project.model.Page;
import project.model.ResultPage;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author VG
 * @version 0.1
 */
@Controller
@RequiredArgsConstructor
public class SearchSystemController {
    public final PageServise pageServise;
    public final LemmaServise lemmaServise;
    public final IndexServise indexServise;
    private LemCreator lemCreator = new LemCreator();

    public  JSONObject find(String searchRequest, String site, String offset, String limit){
        JSONObject result = new JSONObject();
        int offsetInt;
        int limitInt;
        int site_id;
        if(site.isEmpty()){
            site_id = 0;
        }
        else{
            site_id = Integer.parseInt(site);
        }
        if(offset.isEmpty()){
            offsetInt = 0;
        }
        else{
            offsetInt = Integer.parseInt(offset);
        }
        if (limit.isEmpty()){
            limitInt = 20;
        }
        else{
            limitInt = Integer.parseInt(limit);
        }
        JSONArray results = new JSONArray();
        HashMap<String, Integer> lemsOfSearchRequest = new HashMap<>();
        List<Lemma> lemsFromDB = new ArrayList<>();
        lemsOfSearchRequest = lemCreator.getLem(searchRequest);
        lemsFromDB = lemmaServise.findLemmaList(lemsOfSearchRequest);
        Collections.sort(lemsFromDB);
        List<Index> ind = indexServise.findIndexListByLemmaId(lemsFromDB.get(0).getId());
        for(int i =1; i < lemsFromDB.size();i++){
            ind = removePages(lemsFromDB.get(i), ind);
        }
        for (Index index: ind){
            ResultPage resultPage = new ResultPage();
            Page page = pageServise.getPageById(index.getPageid()).get();
            if(page.getId()==site_id){resultPage.setSite_id(page.getSiteId());}
            else{continue;}
            resultPage.setUrl(page.getPath());
            resultPage.setTitle();
            resultPage.setSnippet(getSnippet(page.getContent(), searchRequest));
            resultPage.setRelevance(relevance(index.getPageid(), lemsFromDB));
            results.put(resultPage);

        }
        if(results.isEmpty()){
            result.put("result",false);
            result.put("error","задан пустой поисковой запрос");
            return result;
        }
        result.put("result", true);
        result.put("count", results.length());
        JSONArray resultsList = new JSONArray();
        result.put("data", resultsList);
        return result;
    }
    public  float relevance(int page_id, List<Lemma> lems){
        float rank = 0;
        for(Lemma lemma: lems){
            Index index = indexServise.findIndexByPage_idAndLemm_id(page_id, lemma.getId());
            rank += index.rank;
        }
        return rank;
    }
    public  List<Index> removePages(Lemma lem, List<Index> ind){
        List<Index> finPages = new ArrayList<>();
        for(Index index: ind){
            List<Index> pagesId = indexServise.findIndexListByPageId(index.getPageid());
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
        String regString = "(("+wordList[0]+")([\\s\\S]+?)("+wordList[wordList.length]+"))";
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
}

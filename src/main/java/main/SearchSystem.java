package main;


import connections.dataBase.PageController;
import connections.dataBase.SearchRequestHandler;
import connections.dataBase.SiteController;
import connections.sites.SiteConnect;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author VG
 * @version 0.1
 */
public class SearchSystem {
    private SearchRequestHandler requests = new SearchRequestHandler();
    private PageController pageDB = new PageController();
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
        lemsFromDB = requests.findLemmaList(lemsOfSearchRequest);
        Collections.sort(lemsFromDB);
        List<Index> ind = requests.findIndexList("lemma_Id",String.valueOf(lemsFromDB.get(0).getId()));
        for(int i =1; i < lemsFromDB.size();i++){
            ind = removePages(lemsFromDB.get(i), ind);
        }
        for (Index index: ind){
            ResultPage resultPage = new ResultPage();
            Page page = pageDB.get("id", String.valueOf(index.getPage_id()));
            if(page.getId()==site_id){resultPage.setSite_id(page.getSite_id());}
            else{continue;}
            resultPage.setUrl(page.getPath());
            resultPage.setTitle();
            resultPage.setSnippet(getSnippet(page.getContent(), searchRequest));
            resultPage.setRelevance(relevance(index.getPage_id(), lemsFromDB));
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
            Index index = requests.findIndexList(String.valueOf(page_id), String.valueOf(lemma.getId())).get(0);
            rank += index.rank;
        }
        return rank;
    }
    public  List<Index> removePages(Lemma lem, List<Index> ind){
        List<Index> finPages = new ArrayList<>();
        for(Index index: ind){
            List<Index> pagesId = requests.findIndexList("page_id", String.valueOf(index.getPage_id()));
            for(Index page: pagesId){
                if(page.getLemma_id()!= lem.getId()){
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

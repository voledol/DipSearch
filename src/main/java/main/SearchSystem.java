package main;


import connections.dataBase.PageController;
import connections.dataBase.SearchRequestHandler;
import connections.dataBase.SiteController;
import connections.sites.SiteConnect;
import model.*;
import java.io.IOException;
import java.util.*;

/**
 * @author VG
 * @version 0.1
 * @deprecated
 */
public class SearchSystem {
    public String searchRequest;
    private SearchRequestHandler requests = new SearchRequestHandler();
    private PageController pageDB = new PageController();
    private LemCreator lemCreator = new LemCreator();

    public  List<ResultPage> find(String searchRequest, String site, String offset, String limit){
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
        List<ResultPage> results = new ArrayList<>();
        HashMap<String, Integer> lemsWFR = new HashMap<>();
        List<Lemma> lems = new ArrayList<>();
        try {
             lemsWFR = lemCreator.getLem(searchRequest);
            lems = requests.findLemmaList(lemsWFR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(lems);
        List<Index> ind = requests.findIndexList("lemma_Id",String.valueOf(lems.get(0).getId()));
        for(int i =1; i < lems.size();i++){
            ind = removePages(lems.get(i), ind);
        }
        for (Index index: ind){
            ResultPage resultPage = new ResultPage();
            Page page = pageDB.get("id", String.valueOf(index.getPage_id()));
            if(page.getId()==site_id){resultPage.setSite_id(page.getSite_id());}
            else{continue;}
            resultPage.setUrl(page.getPath());
            resultPage.setTitle();
            resultPage.setSnippet(getSnippet(page.getContent()));
            resultPage.setRelevance(relevance(index.getPage_id(), lems));
            results.add(resultPage);

        }
        for(ResultPage page: results){
            System.out.println(page.getUrl());
            System.out.println(page.getTitle());
            System.out.println(page.getRelevance());
        }
        if(results.isEmpty()){
            return results = new ArrayList<>();
        }
        return results;
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
    public String getSnippet(String content){
        return "не реализовано";
    }
}

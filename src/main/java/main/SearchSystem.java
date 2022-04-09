package main;


import connections.dataBase.PageController;
import connections.dataBase.SearchRequestHandler;
import connections.dataBase.SiteController;
import model.*;
import java.io.IOException;
import java.util.*;

public class SearchSystem {
    public String searchRequest;
    private SearchRequestHandler requests = new SearchRequestHandler();
    private PageController pageDB = new PageController();
    private LemCreator lemCreator = new LemCreator();

    public  List<ResultPage> find(String searchRequest){
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
            Page page = pageDB.get("id", String.valueOf(index.getPage_id()));
            ResultPage resultPage = new ResultPage();
            resultPage.setUrl(page.getPath());
            resultPage.setTitle();
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
}

package main;

import model.Index;
import model.Lemma;
import model.ResultPage;

import java.io.IOException;
import java.util.*;

public class SearchSystem {
    public String searchRequest;


    public static List<ResultPage> find(String searchRequest){
        List<ResultPage> results = new ArrayList<>();
        HashMap<String, Integer> lemsWFR = new HashMap<>();
        List<Lemma> lems = new ArrayList<>();
        try {
             lemsWFR = LemMaker.getLem(searchRequest);
            lems = Hibernate.findLemmaList(lemsWFR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(lems);
        List<Index> ind = Hibernate.findPageList(lems.get(0));
        for(int i =1; i < lems.size();i++){
            ind = Hibernate.removePages(lems.get(i), ind);
        }

        for (Index index: ind){
            ResultPage page = DBConnection.getPage(index.getPage_id());
            page.setRelevance(SearchSystem.relevance(index.getPage_id(), lems));
            results.add(page);
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
    public static float relevance(int page_id, List<Lemma> lems){
        float rank = 0;
        for(Lemma lemma: lems){
            rank += DBConnection.getRank(page_id,lemma.getId());
        }
        return rank;

    }
}

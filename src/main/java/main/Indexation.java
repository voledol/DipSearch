package main;

import connections.dataBase.IndexListCRUD;
import connections.dataBase.LemmaCRUD;
import connections.dataBase.PageCRUD;
import connections.sites.SiteConnect;
import model.Index;
import model.Lemma;
import model.Page;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.*;

public class Indexation {
    private PageCRUD pageDB = new PageCRUD();
    private LemmaCRUD lemmaDB = new LemmaCRUD();
    private IndexListCRUD indexListCRUD = new IndexListCRUD();
    private SiteConnect connectSite = new SiteConnect();
    private LemMaker lemMaker = new LemMaker();
    public synchronized  void indexPage(String  pageUrl) {
        connectSite.getConnection(pageUrl);
        HashMap<String, Integer> titleLemm = new HashMap<>();
        HashMap<String, Integer> bodyLemm = new HashMap<>();
        Page page = (Page) pageDB.read("url", pageUrl);
        try {
                String indexPage =pageUrl;
                titleLemm = lemMaker.getLem((connectSite.getContent("title")).toString());
                bodyLemm = lemMaker.getLem(connectSite.getContent("body").toString());
                HashMap<String, Float> rank = calculateLemmaRank(titleLemm, bodyLemm);
                for (Map.Entry<String, Integer> entry : titleLemm.entrySet()) {
                    if (bodyLemm.containsKey(entry.getKey())) {
                        bodyLemm.put(entry.getKey(), entry.getValue() + bodyLemm.get(entry.getKey()));
                    }
                }
                for (Map.Entry<String, Integer> entry : bodyLemm.entrySet()) {
                    Index index = new Index();
                    Lemma lemma = new Lemma();
                    lemma.setSite_id(page.getSite_id());
                    index.setPage_id(page.getId());
                    lemma = (Lemma) lemmaDB.read("lemma", entry.getKey());
                    if (lemma != null) {
                        lemma.setFrequency(lemma.getFrequency() + entry.getValue());
                        lemmaDB.update(lemma);
                        index.setLemma_id(lemma.getId());
                    } else {
                        lemma.setLemma(entry.getKey());
                        lemma.setFrequency(1);
                        lemmaDB.create(lemma);
                        index.setLemma_id(((Lemma)lemmaDB.read("lemma", entry.getKey())).getId());
                    }
                    index.setRank(rank.get(entry.getKey()));
                    indexListCRUD.create(index);
                }
                titleLemm.clear();
                bodyLemm.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }}
    public static HashMap<String, Float> calculateLemmaRank(HashMap<String, Integer> titleLemm, HashMap<String, Integer> bodyLemm){
        float rank;
        float tittleRank;
        float bodyRank;
        HashMap<String, Float> lemmaRank = new HashMap<>();
        for(Map.Entry<String, Integer> entry: bodyLemm.entrySet()){
            if(titleLemm.containsKey(entry.getKey())){
                tittleRank = titleLemm.get(entry.getKey());
                bodyRank = 0.8F * entry.getValue();
                rank = tittleRank + bodyRank;
                lemmaRank.put(entry.getKey(), rank);
            }
            else{
                rank =  1 + 0.8f*entry.getValue();
                lemmaRank.put(entry.getKey(), rank);
            }
        }
        return lemmaRank;
    }


}


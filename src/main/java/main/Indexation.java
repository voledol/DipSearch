package main;

import model.Index;
import model.Lemma;
import model.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Indexation {
    public   String siteURl;
    public static Connection.Response response;
    public static int columns;


    public static Connection.Response connect(String url) {

        try {
            response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .referrer("http://www.google.com").timeout(10000)
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static List<String> getTitle(String url) throws IOException {
        Document doc = connect(url).parse();
        Elements element = doc.select("title");
        List<String> lemm = element.eachText();
        return lemm;
    }

    public static List<String> getBody(String url) {
        List<String> body = new ArrayList<>();
        try {
            Document doc = connect(url).parse();
            Elements element = doc.select("Body");
            body = element.eachText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;

    }
    public synchronized static void indexPage(String  pageUrl) {
        HashMap<String, Integer> titleLemm = new HashMap<>();
        HashMap<String, Integer> bodyLemm = new HashMap<>();
        Page page =  Hibernate.getPage(pageUrl);
        try {
                String indexPage =pageUrl;
                titleLemm = LemMaker.getLem(getBody(indexPage).toString());
                bodyLemm = LemMaker.getLem(getTitle(indexPage).toString());
                HashMap<String, Float> rank = calculateRank(titleLemm, bodyLemm);
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
                    if (Hibernate.findLemm(entry.getKey())) {
                        Hibernate.updateLemma(entry.getKey());
                        index.setLemma_id(Hibernate.findLemma(entry.getKey()).getId());
                    } else {
                        lemma.setLemma(entry.getKey());
                        lemma.setFrequency(1);
                        Hibernate.save(lemma);
                        index.setLemma_id(Hibernate.findLemma(lemma.getLemma()).getId());
                    }
                    index.setRank(rank.get(entry.getKey()));
                    Hibernate.save(index);
                }
                titleLemm.clear();
                bodyLemm.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }}
    public static HashMap<String, Float> calculateRank(HashMap<String, Integer> titleLemm,HashMap<String, Integer> bodyLemm){
        float rank;
        float tittleRank;
        float bodyRank;
        HashMap<String, Float> lemmRank = new HashMap<>();
        for(Map.Entry<String, Integer> entry: bodyLemm.entrySet()){
            if(titleLemm.containsKey(entry.getKey())){
                tittleRank = titleLemm.get(entry.getKey());
                bodyRank = 0.8F * entry.getValue();
                rank = tittleRank + bodyRank;
                lemmRank.put(entry.getKey(), rank);
            }
            else{
                rank =  1 + 0.8f*entry.getValue();
                lemmRank.put(entry.getKey(), rank);
            }
        }
        return lemmRank;
    }


}


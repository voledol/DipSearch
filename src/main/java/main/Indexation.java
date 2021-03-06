package main;

import connections.dataBase.IndexController;
import connections.dataBase.LemmaController;
import connections.dataBase.PageController;
import connections.sites.SiteConnect;
import model.Index;
import model.Lemma;
import model.Page;
import java.io.IOException;
import java.util.*;
/**
 * Класс построничной индексации сайта
 * @author VG
 * @version 0.1
 * **/
public class Indexation {
    /**Поле создания PageController {@link PageController}*/
    private PageController pageDB = new PageController();
    /**Поле создания LemmaController {@link LemmaController}*/
    private LemmaController lemmaDB = new LemmaController();
    /**Поле создания IndexController {@link IndexController}*/
    private IndexController indexListCRUD = new IndexController();
    /**Поле создания SiteConnect {@link SiteConnect}*/
    private SiteConnect connectSite = new SiteConnect();
    /**Поле создания LemmCreator {@link LemCreator}*/
    private LemCreator lemCreator = new LemCreator();
    /**Поле подсчета количества созданных иникальных лемм*/
    public static int lemmCount = 0;
    /** Функция индексации страницы по адресу
     * @param pageUrl - Адресс страницы в установленном формате
     * **/
    public void indexPage(String  pageUrl) {
        connectSite.getConnection(pageUrl);
        HashMap<String, Integer> titleLemm = new HashMap<>();
        HashMap<String, Integer> bodyLemm = new HashMap<>();
        String correctUrl = getCorrectUrl(pageUrl);
        Page page = pageDB.get("path",correctUrl);
        if(page.getPath()==null) {
            page = pageDB.get("path", "/");
        }
        String indexPage =pageUrl;
        titleLemm = lemCreator.getLem((connectSite.getContent("title")).toString());
        bodyLemm = lemCreator.getLem(connectSite.getContent("body").toString());
        HashMap<String, Float> rank = calculateLemmaRank(titleLemm, bodyLemm);
        for (Map.Entry<String, Integer> entry : titleLemm.entrySet()) {
            if (bodyLemm.containsKey(entry.getKey())) {
                bodyLemm.put(entry.getKey(), entry.getValue() + bodyLemm.get(entry.getKey()));
            }
        }
        for (Map.Entry<String, Integer> entry : bodyLemm.entrySet()) {
           Index index = indexCreator(page, entry.getKey(), entry.getValue());
            index.setRank(rank.get(entry.getKey()));
            indexListCRUD.add(index);
        }
        titleLemm.clear();
        bodyLemm.clear();

    }
    public Index indexCreator(Page page, String entry, Integer value){

        Index index = new Index();
        Lemma lemma = new Lemma();

        lemma =lemmaDB.get("lemma", entry);
        if (lemma.getLemma() != null) {
            lemma.setFrequency(lemma.getFrequency() + value);
            lemmaDB.update(lemma);
            index.setLemma_id(lemma.getId());
        } else {
            lemma.setLemma(entry);
            lemma.setFrequency(1);
            lemmaDB.add(lemma);
            lemmCount++;
            index.setLemma_id((lemmaDB.get("lemma", entry)).getId());
        }
        lemma.setSite_id(page.getSite_id());
        index.setPage_id(page.getId());
        return index;
    }
    /**Функция расчета ранга леммы
     * @param bodyLemm - Леммы тела сайта
     * @param titleLemm - леммы заголовка сайта
     * @return Возвращает список лемм с рачитанными рангами
     * */
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
    public String getCorrectUrl(String url){
        String[] split = url.split("/");
        StringBuilder correctUrl = new StringBuilder();
        if(split.length<3){
            return "";
        }
        for(int i = 3; i< split.length;i++){
            correctUrl.append("/").append(split[i]);
        }
       return String.valueOf(correctUrl);
    }



}


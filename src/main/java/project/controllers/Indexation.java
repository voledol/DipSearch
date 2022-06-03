package project.controllers;


import org.springframework.stereotype.Controller;
import project.services.IndexServise;
import project.services.LemmaServise;
import project.services.PageServise;
import project.services.SiteServise;
import lombok.RequiredArgsConstructor;
import project.model.Index;
import project.model.Lemma;
import project.model.Page;

import java.util.*;
/**
 * Класс построничной индексации сайта
 * @author VG
 * @version 0.1
 * **/
@Controller
@RequiredArgsConstructor
public class Indexation {
    public final LemCreator lemCreator = new LemCreator();
    public final SiteServise connectSite;
    public final PageServise pageServise;
    public final LemmaServise lemmaServise;
    public final IndexServise indexServise;




    /** Функция индексации страницы по адресу
     * @param pageUrl - Адресс страницы в установленном формате
     * **/
    public void indexPage(String  pageUrl) {
        connectSite.getConnection(pageUrl);
        HashMap<String, Integer> titleLemm = new HashMap<>();
        HashMap<String, Integer> bodyLemm = new HashMap<>();
        String correctUrl = getCorrectUrl(pageUrl);
        Page page = pageServise.getPage(correctUrl, 0);
        if(page.getPath()==null) {
            page = pageServise.getPage(correctUrl, 0);
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
            indexServise.save(index);
        }
        titleLemm.clear();
        bodyLemm.clear();

    }
    public Index indexCreator(Page page, String entry, Integer value){

        Index index = new Index();
        Lemma lemma = new Lemma();

        lemma =lemmaServise.getLemmaByName(entry);
        if (lemma.getLemma() != null) {
            lemma.setFrequency(lemma.getFrequency() + value);
            lemmaServise.updateLemma(lemma);
            index.setLemmaid(lemma.getId());
        } else {
            lemma.setLemma(entry);
            lemma.setFrequency(1);
            lemmaServise.addLemma(lemma);
            index.setLemmaid((lemmaServise.getLemmaByName(entry)).getId());
        }
        lemma.setSiteid(page.getSiteId());
        index.setPageid(page.getId());
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


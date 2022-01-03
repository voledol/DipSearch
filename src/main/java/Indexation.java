import model.Index;
import model.Lemma;
import model.Page;
import org.hibernate.Session;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Indexation {
    public static final String parentSite = "https://dimonvideo.ru/";
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

    public static ArrayList<String> takeUrls(String siteURL) throws SQLException {
        ResultSet urls = null;
        ArrayList<String> indUrls = new ArrayList<>();
        String sql = "SELECT path FROM search_engine.search_engine;";
        indUrls.add(siteURL);
        try {
            urls = DBConnection.getConnection().createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columns = urls.getMetaData().getColumnCount();
        while (urls.next()) {
            for (int i = 1; i <= columns; i++) {
                indUrls.add(siteURL + urls.getString(i));
            }
        }
        return indUrls;

    }

    public static void insertPages(ArrayList<String> urls) {
        for (String url : urls) {
            Hibernate.save(createPage(url));
        }
    }

    public static void indexation() {
        HashMap<String, Integer> titleLemm = new HashMap<>();
        HashMap<String, Integer> bodyLemm = new HashMap<>();
        List<Page> pages = Hibernate.getPage();
        try {
            for (int i = 0; i < pages.size(); i++) {
                String page = pages.get(i).getPath();
                titleLemm = LemMaker.getLem(getBody(page).toString());
                bodyLemm = LemMaker.getLem(getTitle(page).toString());
                HashMap<String, Float> rank = calculateRank(titleLemm, bodyLemm);
                for (Map.Entry<String, Integer> entry : titleLemm.entrySet()) {
                    if (bodyLemm.containsKey(entry.getKey())) {
                        bodyLemm.put(entry.getKey(), entry.getValue() + bodyLemm.get(entry.getKey()));
                    }
                }
                for (Map.Entry<String, Integer> entry : bodyLemm.entrySet()) {
                    Index index = new Index();
                    Lemma lemma = new Lemma();
                    index.setPage_id(pages.get(i).getId());
                    if(Hibernate.findLemm(entry.getKey())){
                        Hibernate.updateLemma(entry.getKey());
                       index.setLemma_id(Hibernate.findLemma(entry.getKey()).getId());
                    }
                    else {
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Hibernate.closeSession();
    }
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

    public static String getParentSite() {
        return parentSite;
    }
    public static Page createPage(String url){
        try{
            response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .referrer("http://www.google.com")
                    .execute();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Page page = new Page();
        try {
            Document doc = response.parse();
            Elements content = doc.select("html");
            page.setPath(url);
            page.setContent(String.valueOf(content));
            page.setCode(response.statusCode());
            }
        catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}


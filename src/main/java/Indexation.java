import model.Lemma;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexation {
    public final String parentSite = "https://dimonvideo.ru/";
    public static Connection.Response response;
    public static Connection.Response connect(String url){
        try{
            response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .referrer("http://www.google.com").timeout(50000)
                    .execute();
        }
        catch (IOException e){
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
    public static List<String> getBody(String url) throws IOException {
        Document doc = connect(url).parse();
        Elements element = doc.select("Body");
        List<String> lemm = element.eachText();
        return lemm;
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

        int columns = urls.getMetaData().getColumnCount();
        while(urls.next()){
            for (int i = 1; i <= columns; i++){
                indUrls.add(siteURL+urls.getString(i));
            }
        }
        return indUrls;

    }
    public static void indexation(ArrayList<String> urls) throws IOException {
        HashMap<String, Integer> titleLemm;
        HashMap<String, Integer> bodyLemm;
        for(String url: urls){
            titleLemm = LemMaker.getLem(getBody(url).toString());
            bodyLemm = LemMaker.getLem(getTitle(url).toString());
            HashMap<String, Float> rank = calculateRank(titleLemm,bodyLemm);
            for(Map.Entry<String, Integer> entry: titleLemm.entrySet()){
                if(bodyLemm.containsKey(entry.getKey())){
                    bodyLemm.put(entry.getKey(), entry.getValue()+ bodyLemm.get(entry.getKey()));
                }
            }
            for(Map.Entry<String, Integer> entry: bodyLemm.entrySet()){

            }

        }

    }
    public static HashMap<String, Float> calculateRank(HashMap<String, Integer> titleLemm,HashMap<String, Integer> bodyLemm){
        float rank;
        HashMap<String, Float> lemmRank = new HashMap<>();
        for(Map.Entry<String, Integer> entry: bodyLemm.entrySet()){
            if(titleLemm.containsKey(entry.getKey())){
                rank = (float) (titleLemm.get(entry.getKey()) + 0.8 * entry.getValue());
                lemmRank.put(entry.getKey(), rank);
            }
            else{
                rank = (float) ( 1 + 0.8*entry.getValue());
                lemmRank.put(entry.getKey(), rank);
            }
        }
        return lemmRank;
    }

    public String getParentSite() {
        return parentSite;
    }
}

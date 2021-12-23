import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class ColumCreator {
     public static HashSet<sitePage> pages = new HashSet<>();
     public static Connection.Response response = null;

     public static Connection.Response connect(String url){
          try{
               response = Jsoup.connect(url)
                       .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                       .referrer("http://www.google.com")
                       .execute();
          }
          catch (IOException e){
               e.printStackTrace();
          }
          return response;
     }
     public static void createColum(Nodelink nodelink){
          String url = nodelink.getUrl();
          ColumCreator.connect(url);
          try {
               Document doc = response.parse();
               Elements content = doc.select("html");
               sitePage page = new sitePage();
               page.setPath(url);
               page.setContent(String.valueOf(content));
               page.setCode(response.statusCode());
               DBConnection.insertPage(page);
          }
          catch (IOException e) {
               e.printStackTrace();
          }
     }
}

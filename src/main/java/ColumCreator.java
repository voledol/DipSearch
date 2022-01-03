import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ColumCreator {
     public static Connection.Response response;

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
     public static String getTitle(String url){
          ColumCreator.connect(url);
          String title = "";
          try {
               Document doc = response.parse();
               Elements content = doc.select("title");
               title = content.text();
          }
          catch (IOException e) {
               e.printStackTrace();
          }
          return title;

     }
}

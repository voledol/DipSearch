package main;

import model.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ColumCreator {
     public static Connection.Response response;

     public static Connection.Response connect(String url){
          try{
               response = Jsoup.connect(url)
                       .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                       .referrer("http://www.google.com")
                       .ignoreHttpErrors(true)
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
                    String[] url1 = nodelink.getUrl().split("/");
                    String urlfin = url1[0] + "//"+ url1[1] + url1[2];

                    Document doc = response.parse();
                    Elements content = doc.select("html");
                    Page page = new Page();
                    page.setPath(nodelink.getUrl().replaceAll(urlfin,""));
                    page.setContent(content.text().replaceAll("'",""));
                    page.setCode(response.statusCode());
                    page.setSite_id(DBConnection.getSiteId(urlfin));
                    DBConnection.insertPage(page);
//                    DBConnection.updateStatus(urlfin);
               }
               catch (IOException | SQLException e) {
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
     public static void createColum2(String url2){
          String url = url2;
          ColumCreator.connect(url);
          try {
               String[] url1 = url2.split("/");
               String urlfin = url1[0] + "//"+ url1[1] + url1[2];

               Document doc = response.parse();
               Elements content = doc.select("html");
               Page page = new Page();
               page.setPath(url2.replaceAll(urlfin,""));
               page.setContent(content.text().replaceAll("'",""));
               page.setCode(response.statusCode());
               page.setSite_id(DBConnection.getSiteId(urlfin));
               DBConnection.insertPage(page);
//                    DBConnection.updateStatus(urlfin);
          }
          catch (IOException | SQLException e) {
               e.printStackTrace();
          }
     }
}

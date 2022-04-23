package main;

import connections.dataBase.PageController;
import connections.dataBase.SiteController;
import connections.sites.SiteConnect;
import model.Page;
import model.Site;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;


/**
 * Класс создания объектов класса Page bp Страниц сайта  {@link Page}
 * @author VG
 * @version 0.1
 * **/
public class PageCreator {
    /**Поле создания соединения с сайтом {@link SiteConnect}*/
     public static SiteConnect connect = new SiteConnect();
    /**Поле создания PageControllera для добавления объектом Page в БД {@link PageController}*/
     public static PageController pageDB = new PageController();
    /**Поле создания SiteController для запроса параметров сайта с БД {@link SiteController}*/
     public static SiteController siteDB = new SiteController();
    /**Поле подсчета количества созданных обектов класса Page {@link Page}*/
     public static int pageCount = 0;

     /**Функция создания объекта класса Page {@link Page} по данным полученным со странци сайта
      * @param url - url страницы сайта*
      * @return возваращает объект класса Page {@link Page}
      */
         public  Page createPage(String url){
         Page page = new Page();
               connect.getConnection(url);
                    String[] urlSplit = url.split("/");
                    String urlFin = urlSplit[0] + "//"+ urlSplit[1] + urlSplit[2];
                    Elements content = connect.getContent("html");
                    page.setPath(url.replaceAll(urlFin,""));
                    if(page.getPath().isEmpty()){
                        page.setPath("/");
                    }
                    page.setContent(content.text().replaceAll("'",""));
                    page.setCode(connect.response.statusCode());
                    page.setSite_id(getSiteId(url));
                    pageDB.add(page);
                    pageCount++;
               return page;
    }

    /**
     * @return количество созданных страниц за вермя работы
     */
    public static int getPageCount(){
        return pageCount;
    }
    public int getSiteId(String url){
        int id = 0;
        for(Site str: Main.allowedSitesList){
            if(url.contains(str.getUrl())){
                id = str.getId();
            }
        }
        return id;
    }
}

package main;

import connections.dataBase.PageController;
import connections.dataBase.SiteController;
import connections.sites.SiteConnect;
import model.Page;
import model.Site;
import org.jsoup.nodes.Document;


/**
 * Класс создания объектов класса Page bp Страниц сайта  {@link Page}
 * @autor VG
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
                    Document content = connect.getContent("html");
                    page.setPath(url.replaceAll(urlFin,""));
                    page.setContent(content.text().replaceAll("'",""));
                    page.setCode(connect.response.statusCode());
                    Site site = (Site) siteDB.get("url",urlFin);
                    page.setSite_id(site.getId());
                    pageDB.add(page);
                    pageCount++;
               return page;
    }
    public static int getPageCount(){
        return pageCount;
    }
}

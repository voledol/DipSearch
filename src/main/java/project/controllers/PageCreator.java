package project.controllers;

import project.services.PageServise;
import project.services.SiteServise;
import lombok.RequiredArgsConstructor;
import project.model.Page;
import project.model.Site;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;


/**
 * Класс создания объектов класса Page bp Страниц сайта  {@link Page}
 * @author VG
 * @version 0.1
 * **/
@Controller
@RequiredArgsConstructor
public class PageCreator {
    public SiteServise connect;
    public final PageServise pageServise;
    public final SiteServise siteServise;

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
                    page.setSiteId(getSiteId(url));
                    pageServise.savePage(page);

               return page;
    }

    /**
     * @return количество созданных страниц за вермя работы
     */

    public int getSiteId(String url){
        int id = 0;
        for(Site str: siteServise.getAllSites()){
            if(url.contains(str.getUrl())){
                id = str.getId();
            }
        }
        return id;
    }
}

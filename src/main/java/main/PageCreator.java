package main;

import connections.dataBase.PageController;
import connections.dataBase.SiteController;
import connections.sites.SiteConnect;
import model.Page;
import model.Site;
import org.jsoup.nodes.Document;



public class PageCreator {
     public static SiteConnect connect = new SiteConnect();
     public static PageController pageDB = new PageController();
     public static SiteController siteDB = new SiteController();
     public static int pageCount = 0;

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

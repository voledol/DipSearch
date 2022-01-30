package main;

import connections.dataBase.PageCRUD;
import connections.dataBase.SiteCRUD;
import connections.sites.SiteConnect;
import model.Page;
import model.Site;
import org.jsoup.nodes.Document;

import java.sql.SQLException;

public class PageCreator {
     public SiteConnect connect = new SiteConnect();
     private PageCRUD pageDB = new PageCRUD();
     private SiteCRUD siteDB = new SiteCRUD();


    public PageCreator(PageCRUD pageDB) {
        this.pageDB = pageDB;
    }

    public  Page createPage(String url){
         Page page = new Page();
               connect.getConnection(url);
                    String[] urlSplit = url.split("/");
                    String urlFin = urlSplit[0] + "//"+ urlSplit[1] + urlSplit[2];
                    Document content = connect.getContent("html");
                    page.setPath(url.replaceAll(urlFin,""));
                    page.setContent(content.text().replaceAll("'",""));
                    page.setCode(connect.response.statusCode());
                    Site site = (Site) siteDB.read("url",urlFin);
                    page.setSite_id(site.getId());
                    pageDB.create(page);
               return page;
     }
}

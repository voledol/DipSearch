package project.services;
import project.repositoryes.SiteConnection;
import project.repositoryes.SiteRepository;
import lombok.RequiredArgsConstructor;
import project.model.Site;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteServise implements SiteConnection {
    public Connection.Response response;
    public  final SiteRepository siteRepository;

    @Override
    public Connection.Response getConnection (String url) {
        try{
            response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .timeout(10000)
                    .execute();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Elements getContent (String selector) {
        Elements content = new Elements();
        try{
            Document doc = response.parse();
            content = doc.select("html");
        }
        catch (Exception e ){
            e.printStackTrace();
        }
        return content ;
    }
    public List<Site> getAllSites(){
        return siteRepository.findAll();
    }
}

package connections.sites;

import interfaсes.SiteConnection;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteConnect implements SiteConnection {
public Connection.Response response;

    @Override
    public Connection.Response getConnection(String url) {
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

    @SneakyThrows
    @Override
    public Document getContent(String selector) {
        Document doc = response.parse();
        Elements content = doc.select("html");
        return doc;
    }
}

package project;

import project.repositoryes.SiteConnection;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Класс выполнящий соедиение с сайтом
 */
public class SiteConnect implements SiteConnection {
public Connection.Response response;

    /** функция выполняющая соединение с сайтом
     * @param url - адрес сатй
     * @return - возвращает соедние с сайтом
     */
    @Override
    public  Connection.Response getConnection(String url) {
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

    /** функция получения коонтента со страницы по тегу сселектору
     * @param selector - селектор тег html страницы
     * @return - возвращает выбранный по тегу контент страницы
     */
    @SneakyThrows
    @Override
    public Elements getContent(String selector) {
        Elements content = new Elements();
        try{
           Document doc = response.parse();
           content = doc.select("html");
        }
        catch (Exception e ){
            e.printStackTrace();
        }
        return content;
    }
}

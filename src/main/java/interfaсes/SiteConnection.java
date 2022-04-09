package interfaсes;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
/** Интерфейс описывающие подключение к сайтам
 * @autor VG
 * @version 0.1
 **/
public interface SiteConnection {
    Connection.Response getConnection(String url);
    Document getContent(String selector);
}

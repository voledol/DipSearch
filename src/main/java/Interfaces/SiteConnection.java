package Interfaces;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
/**Interface for site connection
 * @author VG
 * @version 0.1
 **/
public interface SiteConnection {
    Connection.Response getConnection(String url);
    Document getContent(String selector);
}

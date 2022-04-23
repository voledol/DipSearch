package Interfaces;

import org.jsoup.Connection;
import org.jsoup.select.Elements;

/**Interface for site connection
 * @author VG
 * @version 0.1
 **/
public interface SiteConnection {
    Connection.Response getConnection(String url);
    Elements getContent(String selector);
}

package project.repositoryes;

import org.jsoup.Connection;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

/**Interface for site connection
 * @author VG
 * @version 0.1
 **/
@Repository
public interface SiteConnection {
    Connection.Response getConnection(String url);
    Elements getContent(String selector);
}

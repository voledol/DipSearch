package project.repositoryes;

import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

/**Interface for site connection
 * @author VG
 * @version 0.1
 **/
@Repository
public interface SiteConnection {
    Connection.Response getConnection(String url);
    Elements getContent (String selector);
    String getContentWithSelector(String selector, Document  doc);

}

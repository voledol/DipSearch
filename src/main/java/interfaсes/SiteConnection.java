package interfaсes;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

public interface SiteConnection {
    Connection.Response getConnection(String url);
    Document getContent(String selector);
}

package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class newMapper {
    public String Site_url;
    public List<String> absUrls= new ArrayList<>();

    public newMapper(String site_url) {
        Site_url = site_url;
    }

    public Set<String> getChildren(String url){
        Set<String> childrenLinks = new HashSet<>();
        try {
            Document doc = Jsoup.connect(url)
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .ignoreHttpErrors(true)
                    .timeout(5000).get();
            Elements links = doc.select("a[href]");
            childrenLinks = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(url))
                    .filter(y -> y.startsWith(Site_url))
                    .filter(v -> !v.contains("#") && !v.contains("?") && !v.contains("'"))
                    .filter(w -> !w.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf))$)"))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return childrenLinks;

    }
    public void map(String startPage){
        Set<String> urls = getChildren(startPage);
        for(String url: urls){
            ColumCreator.createColum2(url);
        }
        urls.clear();

    }
}

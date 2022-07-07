package project.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import project.model.Page;
import project.model.Site;
import project.repositoryes.SiteConnection;


/**
 * Класс создания объектов класса Page bp Страниц сайта  {@link Page}
 *
 * @author VG
 * @version 0.1
 **/
@Service
@RequiredArgsConstructor
public class PageCreatorService {
    public final PageServise pageServise;
    public final SiteConnection siteConnection;
    public final SiteService siteService;

    /**
     * Функция создания объекта класса Page {@link Page} по данным полученным со странци сайта
     *
     * @param url - url страницы сайта*
     * @return возваращает объект класса Page {@link Page}
     */
    @SneakyThrows
    public Page createPage (String url) {
        Connection.Response response = siteConnection.getConnection(url);
        Page page = new Page();
        String[] urlSplit = url.split("/");
        String urlFin = urlSplit[0] + "//" + urlSplit[1] + urlSplit[2];
        Elements content = siteConnection.getContent("html");
        page.setPath(url.replaceAll(urlFin, ""));
        if (page.getPath().isEmpty()) {
            page.setPath("/");
        }
        page.setContent(content.text().replaceAll("'", ""));
        page.setCode(response.statusCode());
        page.setSiteId(getSiteId(url));
        pageServise.savePage(page);
        return page;
    }

    public int getSiteId (String url) {
        int id = 0;
        for (Site str : siteService.getAllSites()) {
            if (url.contains(str.getUrl())) {
                id = str.getId();
            }
        }
        return id;
    }
}

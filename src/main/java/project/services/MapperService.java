package project.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * Класс маппер сайта
 *
 * @version 0.1
 * @autor VG
 **/
@RequiredArgsConstructor
public class MapperService extends RecursiveTask<Set<Nodelink>> {
    /**
     * Поле Узловой ссылки
     */
    private final Nodelink parent;
    /**
     * Поле список Узловых ссылок
     */
    private Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    /** Поле адрес сайта*/
    /**
     * Поле создания PageCreator {@link PageCreatorService}
     */
    public PageCreatorService pageCreator;
    /**
     * Поле создания класса Indexation {@link IndexationService}
     */

    public IndexationService index;
    /**Конструктор класса SiteMapper
     * @param parent - родительская ссылка
     * @see Nodelink*/

    /**
     * Функция Fork Join Pool обработка полученыых ссылок и получения следующих
     *
     * @return возвращает список всех доступных ссылок на сайте
     */
    @Override
    public Set<Nodelink> compute () {
        links.add(parent);
        Set<Nodelink> childrenLinks = this.getChildrenLinks(parent);
        pageCreator.createPage(parent.getUrl());
        index.indexPage(parent.getUrl());
        Set<MapperService> taskList = new HashSet<>();
        for (Nodelink child : childrenLinks) {
            taskList.add((MapperService) new MapperService(child).fork());
        }
        for (MapperService task : taskList) {
            links.addAll(task.join());
        }
        return links;
    }

    /**
     * Функция получения ссылок с одной странице
     *
     * @param parent -  ссылка на страницу с которой необззодимо получить ссылки
     * @return возвращает список доступных на старнице ссылок
     * @throws InterruptedException
     */
    private Set<Nodelink> getChildrenLinks (Nodelink parent) {
        try {

            Elements links = getPageContent(parent);
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))
                    .filter(y -> y.startsWith(index.getSiteTitleUrl(parent.getUrl())))
                    .filter(v -> !v.contains("#") && !v.contains("?") && !v.contains("'"))
                    .filter(w -> !w.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf))$)"))
                    .collect(Collectors.toSet());
            for (String link : absUrls) {
                Nodelink node = new Nodelink(link, parent);
                if (!links.contains(node)) {

                    parent.addSubLink(node);
                }
            }
            sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return parent.getSubLinks();
    }

    /**
     * Функция получения контента страницы в тектовом фале
     *
     * @param node - узловая ссылка
     */
    @SneakyThrows
    public Elements getPageContent (Nodelink node) {
        Connection.Response response;
        response = Jsoup.connect(node.getUrl())
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                .referrer("http://www.google.com")
                .ignoreHttpErrors(true)
                .timeout(10000)
                .execute();
        return response.parse().select("a[href]");
    }
//    @Override
//    public boolean cancel(boolean mayInterruptIfRunning) {
//        list.forEach(t -> t.cancel(true));
//        return super.cancel(mayInterruptIfRunning);
//    }

}


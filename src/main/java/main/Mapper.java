package main;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;
/**Класс маппер сайта
 * @autor VG
 * @version 0.1
 * **/
class SiteMapper extends RecursiveTask<Set<Nodelink>> {
    /**Поле Узловой ссылки*/
    private final Nodelink parent;
    /** Поле список Узловых ссылок*/
    private  Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    /** Поле адрес сайта*/
    private   String SITE_URL;
    /** Поле создания PageCreator {@link PageCreator}*/
    public PageCreator pageCreator = new PageCreator();
    /**Поле создания класса Indexation {@link Indexation}*/
    public Indexation idex = new Indexation();
    /**Конструктор класса SiteMapper
     * @param parent - родительская ссылка
     * @see Nodelink*/
    public SiteMapper(Nodelink parent) {
        this.parent = parent;
        this.SITE_URL = parent.getUrl();
    }

    /**Функция Fork Join Pool обработка полученыых ссылок и получения следующих
     * @return возвращает список всех доступных ссылок на сайте */
    @Override
    public Set<Nodelink> compute() {
        links.add(parent);
        Set<Nodelink> childrenLinks = this.getChildrenLinks(parent);
        pageCreator.createPage(parent.getUrl());
        idex.indexPage(parent.getUrl());
        Set<SiteMapper> taskList = new HashSet<>();
        for (Nodelink child : childrenLinks) {
            taskList.add((SiteMapper) new SiteMapper(child).fork());
        }
        for (SiteMapper task : taskList) {
            links.addAll(task.join());
        }
        return links;
    }
    /**Функция получения ссылок с одной странице
     * @param  parent -  ссылка на страницу с которой необззодимо получить ссылки
     *@return возвращает список доступных на старнице ссылок
     @throws InterruptedException*/
    private Set<Nodelink> getChildrenLinks(Nodelink parent) {
        try {
            String url = SITE_URL;
            Elements doc = getPageContent(parent);
            Elements links = doc.select("a[href]");
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))
                    .filter(y -> y.startsWith(SITE_URL))
                    .filter(v -> !v.contains("#") && !v.contains("?") && !v.contains("'"))
                    .filter(w -> !w.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf))$)"))
                    .collect(Collectors.toSet());
            for (String link : absUrls) {
                Nodelink node = new Nodelink(link, parent);
                if (!links.contains(node)){

                    parent.addSubLink(node);
                }
            }
            sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return parent.getSubLinks();
    }
    /**Функция получения контента страницы в тектовом фале
     * @param node - узловая ссылка */
    public Elements getPageContent(Nodelink node){
        Main.connect.getConnection(node.getUrl());
        Elements doc = Main.connect.getContent(node.getUrl());
        return doc;
    }

}


package main;

import model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

class SiteMapper extends RecursiveTask<Set<Nodelink>> {
    private final Nodelink parent;
    private  Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    private   String SITE_URL;
    public PageCreator pageCreator = new PageCreator();
    public Indexation idex = new Indexation();


    public SiteMapper(Nodelink parent) {
        this.parent = parent;
        this.SITE_URL = parent.getUrl();
    }


    @Override
    public Set<Nodelink> compute() {
        links.add(parent);
        Set<Nodelink> childrenLinks = this.getChildrenLinks(parent);
        pageCreator.createPage(parent.getUrl());
        idex.indexPage(parent.getUrl());// остановился вот тут. демай дальше. надо в первый раз не брать url сайта
        Set<SiteMapper> taskList = new HashSet<>();
        for (Nodelink child : childrenLinks) {
            taskList.add((SiteMapper) new SiteMapper(child).fork());
        }
        for (SiteMapper task : taskList) {
            links.addAll(task.join());
        }
        return links;
    }

    private Set<Nodelink> getChildrenLinks(Nodelink parent) {
        try {
            String url = SITE_URL;
            Document doc = getPageContent(parent);
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
    public Document getPageContent(Nodelink node){
        Main.connect.getConnection(node.getUrl());
        Document doc = Main.connect.getContent(node.getUrl());
        return doc;
    }

}


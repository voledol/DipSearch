package main;

import connections.dataBase.PageCRUD;
import connections.sites.SiteConnect;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

class SiteMapper extends RecursiveTask<Set<Nodelink>>{
    private final Nodelink parent;
    private static Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    private static String SITE_URL;
    public PageCreator pageCreator = new PageCreator(new PageCRUD());
    private SiteConnect connect  = new SiteConnect();




    public SiteMapper(Nodelink parent) {
        this.parent = parent;
    }



    @Override
    protected Set<Nodelink> compute() {

        links.add(parent);
        pageCreator.createPage(parent.getUrl());
        Set<Nodelink> childrenLinks = this.getChildrenLinks(getPageContent(parent));
        Set<SiteMapper> taskList = new HashSet<>();
            for (Nodelink child : childrenLinks) {
                taskList.add(new SiteMapper(child));
            }
            for (SiteMapper task : taskList) {

                links.addAll(task.join());
            }
        return links;
    }

    private Set<Nodelink> getChildrenLinks(Document document) {
        try {
            String[] url1 = parent.getUrl().split("/");
            String urlFin = url1[0] + "//"+ url1[1] + url1[2];
            SITE_URL = urlFin;
            Elements links = document.select("a[href]");
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))
                    .filter(y -> y.startsWith(SITE_URL))
                    .filter(v -> !v.contains("#") && !v.contains("?") && !v.contains("'"))
                    .filter(w -> !w.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf))$)"))
                    .collect(Collectors.toSet());
            for (String link : absUrls) {
                Nodelink node = new Nodelink(link, parent);
                if (!SiteMapper.links.contains(node)){
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
        connect.getConnection(node.getUrl());
        Document doc = connect.getContent("html");
        return doc;
    }

}
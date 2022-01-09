package main;

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
    private static Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    private static String SITE_URL;
    public static boolean status;




    public SiteMapper(Nodelink parent) {
        long i = System.currentTimeMillis();
        this.parent = parent;
    }

    @Override
    protected Set<Nodelink> compute() {

        links.add(parent);
        ColumCreator.createColum(parent);
        Indexation.indexPage(parent.getUrl());
        Set<SiteMapper> taskList = new HashSet<>();
            Set<Nodelink> childrenLinks = this.getChildrenLinks(parent);
        if(status){
            for (Nodelink child : childrenLinks) {
                taskList.add((SiteMapper) new SiteMapper(child).fork());
            }
            for (SiteMapper task : taskList) {
                links.addAll(task.join());
            }
        }
        else{
            taskList.clear();
        }

        return links;
    }

    private Set<Nodelink> getChildrenLinks(Nodelink parent) {
        try {
            String[] url1 = parent.getUrl().split("/");
            String urlfin = url1[0] + "//"+ url1[1] + url1[2];
            SITE_URL = urlfin;
            Document doc = Jsoup.connect(parent.getUrl())
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .ignoreHttpErrors(true)
                    .timeout(5000).get();
            Elements links = doc.select("a[href]");
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
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return parent.getSubLinks();
    }

    public static  boolean isWork() {
        return status;
    }

    public static void stop() {
        status = false;
    }
}
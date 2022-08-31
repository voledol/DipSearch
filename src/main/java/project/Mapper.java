package project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import project.services.IndexationService;
import project.services.PageCreatorService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class Mapper extends RecursiveTask<Set<Nodelink>> {
    private final Nodelink parent;

    private Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    private String SITE_URL;
    private PageDuplicateCheck pageCreatorTest = new PageDuplicateCheck();
    private final PageCreatorService pageCreator;
    private final IndexationService indexationService;


    public Mapper (Nodelink parent, PageCreatorService pageCreator, IndexationService indexationService) {
        this.parent = parent;
        this.SITE_URL = parent.getUrl();
        this.pageCreator = pageCreator;
        this.indexationService = indexationService;
    }


    @Override
    public Set<Nodelink> compute () {
        links.add(parent);
        if(!Main.isIndexationRunning){
            return links;
        }
        if(PageDuplicateCheck.existPages.containsKey(parent.getUrl())){
            PageDuplicateCheck.existPages.put(parent.getUrl(), PageDuplicateCheck.existPages.get(parent.getUrl()) +1);
        }
        else {
            PageDuplicateCheck.existPages.put(parent.getUrl(), 1);
        }
        if(pageCreatorTest.isNoneExistPage(parent.getUrl())){
            indexationService.indexPage(parent.getUrl());
        }
        Set<Nodelink> childrenLinks = this.getChildrenLinks(parent);
        Set<Mapper> taskList = new HashSet<>();
        for (Nodelink child : childrenLinks) {
            taskList.add((Mapper) new Mapper(child, pageCreator, indexationService).fork());
        }
        for (Mapper task : taskList) {
            task.join();
            links.addAll(task.join());
        }
        return links;
    }

    private Set<Nodelink> getChildrenLinks (Nodelink parent) {
        try {
            Document doc = Jsoup.connect(parent.getUrl())
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .timeout(10000).ignoreHttpErrors(true).get();
            Elements links = doc.select("a[href]");
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))
                    .filter(y -> y.startsWith(SITE_URL))
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
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return parent.getSubLinks();
    }

}
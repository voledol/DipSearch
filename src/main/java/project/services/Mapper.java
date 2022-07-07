package project.services;

import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import project.Main;

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
public class Mapper extends RecursiveTask<Set<Nodelink>> {
    private final Nodelink parent;
    private Set<Nodelink> links = ConcurrentHashMap.newKeySet();
    public PageCreatorService pageCreator;
    public IndexationService indexationService;

    public Mapper (Nodelink parent, PageCreatorService pageCreatorService, IndexationService indexationService) {
        this.parent = parent;
        this.pageCreator = pageCreatorService;
        this.indexationService = indexationService;
    }


    @Override
    public Set<Nodelink> compute () {
        cancel(Main.isIndexationRunning);
            links.add(parent);
            pageCreator.createPage(parent.getUrl());
            indexationService.indexPage(parent.getUrl());
            Set<Nodelink> childrenLinks = this.getChildrenLinks(parent);
            Set<Mapper> taskList = new HashSet<>();

            for (Nodelink child : childrenLinks) {
                taskList.add((Mapper) new Mapper(child, pageCreator, indexationService).fork());
            }
            for (Mapper task : taskList) {
                links.addAll(task.join());
            }
        return links;
    }

    private Set<Nodelink> getChildrenLinks (Nodelink parent) {
        try {

            Elements links = getPageContent(parent);
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))
                    .filter(y -> y.startsWith(indexationService.getSiteTitleUrl(parent.getUrl())))//дичь
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


    @Override
    public boolean cancel(boolean isIndexing) {
        if(isIndexing){
            return false;
        }
        getPool().shutdownNow();
        return true;
    }

}


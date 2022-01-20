package main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

@Controller
public class DefaultController {
    AvailableSites availableSites = new AvailableSites();


    @RequestMapping("/")
    public String index() {


        List<AvailableSites.Site> sites = availableSites.getAvailableSites();
        for (AvailableSites.Site site : sites) {
            DBConnection.insertSite(site);
        }
        return "index";
    }

    @RequestMapping("/api/startIndexing")
    @ResponseBody
    public String startIndexing() {
        List<String> sites = DBConnection.getSites();
        String sucsess = "true";
        List< SiteMapper> tasks = new ArrayList<>();
//        for(int i = 0; i < sites.size(); i++){
//
//            tasks.add(new SiteMapper(new Nodelink(sites.get(i))));
//
//        }
//        new ForkJoinPool().invokeAll(tasks);
        ForkJoinPool pool1 = new ForkJoinPool(2, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
        ForkJoinPool pool2 = new ForkJoinPool(2, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
        pool1.invoke(new SiteMapper(new Nodelink(sites.get(1))));
        pool1.invoke(new SiteMapper(new Nodelink(sites.get(2))));


//        for(int i = 0; i < sites.size(); i++){
//            Executor executor = Executors.newSingleThreadExecutor();
//            executor.execute(new SiteMapper(new Nodelink(sites.get(i))));
//        }


        return sucsess ;
    }
    @RequestMapping("/api/indexpage")
    @ResponseBody
    public String indexingPage(@RequestParam String url){
        if(DBConnection.isThisSite(url)){
            Indexation.indexPage(url);
            return "{\n" +
                    "\t'result': true\n" +
                    "}\n";
        }
        return "{\n" +
                "\t'result': false,\n" +
                "\t'error': \"Данная страница находится за пределами сайтов, \n" +
                "указанных в конфигурационном файле\"\n" +
                "}\n";
    }

}


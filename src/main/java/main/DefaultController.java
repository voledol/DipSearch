package main;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.*;

@Controller
public class DefaultController {
    public Indexation indexing = new Indexation();
    public static boolean indexation = false;
    PropertyLoader.Site[] sites = Main.propertyes.getAvalibleSites().getAvailableSites();
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/api/startIndexing")
    @ResponseBody
    public String startIndexing() {
        JSONObject ans = new JSONObject();
        List< SiteMapper> tasks = new ArrayList<>();
//        if(Thread.activeCount()>10){
//            ans.put("result", "false");
//            ans.put("erros", "индексация уже запущена");
//        }
//        else{
            ans.put("result", "true");
            Set<Nodelink> pool1 = new ForkJoinPool().invoke(new SiteMapper(new Nodelink(sites[1].getUrl())));
            indexation = true;
    //            ForkJoinPool pool1 = new ForkJoinPool(2, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
//            ForkJoinPool pool2 = new ForkJoinPool(2, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
//            pool1.invoke(new SiteMapper(new Nodelink(sites[1].getUrl())));
////            pool2.invoke(new SiteMapper(new Nodelink(sites[2].getUrl())));
////        }
        return  ans.toString() ;
    }
    @RequestMapping("/api/indexpage")
    @ResponseBody
    public String indexingPage( String url){
        JSONObject ans = new JSONObject();
        String[] urlSplit = url.split("/");
        String urlFin = urlSplit[0] + "//"+ urlSplit[1] + urlSplit[2];
        if(Arrays.asList(sites).contains(urlFin)){
            indexing.indexPage(url);
            ans.put("result","true");

        }
        else {
            ans.put("result","false");
            ans.put("error", "Данная страница находится за пределами сайтов, указанных в конфигурационном файле");
        }
        return ans.toString();
    }
    @RequestMapping("/api/statistics")
    @ResponseBody
    public String statistic(){
        DBstatistics stat = new DBstatistics();
        JSONObject ans = new JSONObject();
        ans.put("result","true");
        ans.put("total" , stat.statistic());
        ans.put("detailed", stat.detailedStatistic());
        return ans.toString();
    }

}


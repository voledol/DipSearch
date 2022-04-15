package main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.*;

/** Class controller for API of search, indexing and statistic system
 * @author  VG
 * @version  0.1
 */
@Controller
public class DefaultController {
    /**
     * Field create new object of Indexation class {@link Indexation}
     */
    public Indexation indexing = new Indexation();
    /**
     * Field of start/stop indexing
     */
    public static boolean indexation = false;
    /**
     * Field assignments sites from application.yml to site massive
     */
    PropertyLoader.Site[] sites = Main.propertyes.getAvalibleSites().getAvailableSites();
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /** Function started indexing
     * @return JSON file with result of starting indexing. true if indexation is started,
     * false if indexation don't started
     */
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

    /** function of indexation for one page
     * @param url - url of page
     * @return JSON file with result of starting indexing. true if indexation is started,
     * false if indexation don't started
     */
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

    /** Function get statistic of system working from DB
     * @return JSON file with total and detailed statistic
     */
    @RequestMapping("/api/statistics")
    @ResponseBody
    public String statistic(){
        DBstatistics stat = new DBstatistics();
        JSONObject ans = new JSONObject();
        ans.put("result","true");
        ans.put("total" , stat.statistic());
        ans.put("detailed", stat.detailedStatistic(sites));
        return ans.toString();
    }
}


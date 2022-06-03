package project.controllers;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.services.SiteServise;
import project.model.Site;

/** Class controller for API of search, indexing and statistic system
 * @author  VG
 * @version  0.1
 */
@Controller
@RequiredArgsConstructor
public class DefaultController {
    /**
     * Field create new object of Indexation class {@link Indexation}
     */
    public final Indexation indexing;
    public final SearchSystemController searchSystem;
    public final SiteServise siteServise;

    /**
     * Field of start/stop indexing
     */
    public static boolean indexation = false;
    /**
     * Field assignments sites from application.yml to site massive
     */

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /** Function started indexing
     * @return JSON file with result of starting indexing. true if indexation is started,
     * false if indexation don't started
     */
    @GetMapping("/startIndexing")
    @ResponseBody
    public JSONObject  startIndexing() {
        JSONObject ans = new JSONObject();
        return ans;
    }

    /** function of indexation for one page
     * @param url - url of page
     * @return JSON file with result of starting indexing. true if indexation is started,
     * false if indexation don't started
     */
    @PostMapping ("/indexpage")
    @ResponseBody
    public JSONObject indexingPage( String url){
        JSONObject ans = new JSONObject();
        String[] urlSplit = url.split("/");
        String urlFin = urlSplit[0] + "//"+ urlSplit[1] + urlSplit[2];
        boolean containSite = false;
        for(Site site: siteServise.getAllSites()){
            if(site.getName().contains(urlFin)){
                containSite = true;
            }
        }
        if(containSite){
            indexing.indexPage(url);
            ans.put("result","true");

        }
        else {
            ans.put("result","false");
            ans.put("error", "Данная страница находится за пределами сайтов, указанных в конфигурационном файле");
        }
        return ans;
    }

    /** Function get statistic of system working from DB
     * @return JSON file with total and detailed statistic
     */
    @GetMapping ("/statistics")
    @ResponseBody
    public JSONObject statistic(){
        DBstatisticsСontroller stat = new DBstatisticsСontroller();
        JSONObject ans = new JSONObject();
        ans.put("result","true"); 
        ans.put("total" , stat.statistic());
//        ans.put("detailed", stat.detailedStatistic(sites));
        return ans;
    }
    @RequestMapping("/search")
    @ResponseBody
    public String search(@RequestParam String query,@RequestParam String site,@RequestParam String offset,@RequestParam String limit){
        JSONObject ans = searchSystem.find(query, site, offset, limit);;
        return ans.toString();
    }
}


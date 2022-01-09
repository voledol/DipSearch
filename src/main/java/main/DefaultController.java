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
        String sucsess;
        if(SiteMapper.isWork()){
            sucsess= "{\n" +
                    "\t'result': false,\n" +
                    "\t'error': \"Индексация уже запущена\"\n" +
                    "}\n";
            return sucsess;
        }
        else{

            for (int i = 0; i < sites.size()-1;i++){
                main.Nodelink rootUrl = new main.Nodelink(sites.get(i));
                SiteMapper map = new SiteMapper(rootUrl);
                DBConnection.updateSiteINDEXING(sites.get(i));
                map.compute();
                DBConnection.updateSiteINDEXED(sites.get(i));
            }
        }
        sucsess = "{\n" +
                "\t'result': true\n" +
                "}\n";
        return sucsess ;
    }
    @RequestMapping("/api/stopIndexing")
    @ResponseBody
    public String stopIndexing(){
        if(!SiteMapper.isWork()){
            return "{\n" +
                    "'result': false,\n" +
                    "'error': \"Индексация не запущена\"\n" +
                    "            }";

        }
        SiteMapper.stop();
            return "{\n" +
                    "\t'result': true\n" +
                    "}\n";
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
//    @RequestMapping("/api/statistics")
//    @ResponseBody
//    public String getStatistic(@RequestParam String url){
//        String result;
//
//        return ;
//    }

}


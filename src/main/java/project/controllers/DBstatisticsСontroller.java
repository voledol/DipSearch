package project.controllers;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;

/**
 * Класс сборщик статистики содержащихс в БД данных о сайтах и процессе работы
 * @author VG
 * @version 0.1
 * **/
@Controller
public class DBstatisticsСontroller {
    /**
     * Функция собирающая общую статистику по сайтам.
     * @return воззвращает с JSON объект с собранной статистикой в формате параметр: значение.
     * **/
    public JSONObject statistic(){
        JSONObject ans = new JSONObject();
//        ans.put("sites", project.Main.sitesCount);
        ans.put("pages", 0);
        ans.put("lemmas", 0);
        ans.put("indexing", DefaultController.indexation);
        return ans;
    }
    /**
     * Функция собирающая детальную статистику по доступным для поиска сайтам.
     * @param site - доступные сайты в файле application.yml
     * @return воззвращает с JSON объект с собранной статистикой в формате параметр: значение.
     * **/
//    public JSONArray detailedStatistic(Site[] site){
//
//        JSONArray ansDetailed = new JSONArray();
//        try {
//            for (int i = 0 ; i < site.length -1;i++ ){
//                Site siteFromDB = siteStatisticGet.get("url", site[i].url);
//                JSONObject ansSite = new JSONObject();
//                ansSite.put("url", siteFromDB.getUrl());
//                ansSite.put("name", siteFromDB.getName());
//                if (siteFromDB.getStatus() == null)
//                {ansSite.put("status", "FAILED");}
//                else{ansSite.put("status", siteFromDB.getStatus());}
//                if (siteFromDB.getStatus_time() == null)
//                {ansSite.put("statusTime", "00:00");}
//                else{ansSite.put("statusTime", siteFromDB.getStatus_time());}
//                if (siteFromDB.getLast_error()==null)
//                {ansSite.put("Error", "No Errors");}
//                else{ansSite.put("Error", siteFromDB.getLast_error());}
//                if(lemmaStatisticGet.getLemsCount(siteFromDB.getId())==0)
//                {ansSite.put("lemmas", "0");}
//                else{ansSite.put("lemmas", lemmaStatisticGet.getLemsCount(siteFromDB.getId()));}
//                ansDetailed.put(ansSite);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return ansDetailed;
//    }
}


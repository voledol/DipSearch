package main;

import connections.dataBase.LemmaController;
import connections.dataBase.SiteController;
import model.Page;
import org.json.JSONObject;
/**
 * Класс сборщик статистики содержащихс в БД данных о сайтах и процессе работы
 * @autor VG
 * @version 0.1
 * **/
public class DBstatistics {
    public SiteController siteStatisticGet = new SiteController();
    public LemmaController lemmaStatisticGet = new LemmaController();
    /**
     * Функция собирающая общую статистику по сайтам.
     * @return воззвращает с JSON объект с собранной статистикой в формате параметр: значение.
     * **/
    public JSONObject statistic(){
        JSONObject ans = new JSONObject();
        ans.put("sites", Main.sitesCount);
        ans.put("pages", PageCreator.pageCount);
        ans.put("lemmas", Indexation.lemmCount);
        ans.put("indexing", DefaultController.indexation);
        return ans;
    }
    /**
     * Функция собирающая детальную статистику по доступным для поиска сайтам.
     * @param site - доступные сайты в файле application.yml
     * @return воззвращает с JSON объект с собранной статистикой в формате параметр: значение.
     * **/
    public JSONObject detailedStatistic(PropertyLoader.Site site){
        PropertyLoader.Site[] sites = Main.propertyes.getAvalibleSites().getAvailableSites();
        JSONObject[] ansDetailed = new JSONObject[sites.length];
            JSONObject ansSite = new JSONObject();
            ansSite.put("url", site.url);
            ansSite.put("name", site.name);
            ansSite.put("status", 0);
            ansSite.put("statusTime", 0);
            ansSite.put("Error", 0);
            ansSite.put("lemmas", "lemCount");
        return ansSite;
    }
}


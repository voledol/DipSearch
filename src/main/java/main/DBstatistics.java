package main;

import model.Page;
import org.json.JSONObject;

public class DBstatistics {

    public JSONObject statistic(){
        JSONObject ans = new JSONObject();
        ans.put("sites", Main.sitesCount);
        ans.put("pages", PageCreator.pageCount);
        ans.put("lemmas", Indexation.lemmCount);
        ans.put("indexing", DefaultController.indexation);
        return ans;
    }
    public JSONObject detailedStatistic(){
        // get sites lict;
        // get site stictic;
        JSONObject ansDetailed = new JSONObject();
        ansDetailed.put("url", 1);
        ansDetailed.put("name", 1);
        ansDetailed.put("status", 1);
        ansDetailed.put("statusTime", 1);
        ansDetailed.put("Error", 1);
        ansDetailed.put("lemmas", 1);

        return ansDetailed;
    }
}


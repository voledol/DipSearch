package project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PageDuplicateCheck {
    public static String pageUrl;
    public static int pageCount = 0;
    public static HashMap<String, Integer> existPages = new HashMap<String, Integer>();

    public  Boolean isNoneExistPage (String url){
        if((existPages.containsKey(url)&&(existPages.get(url)==1))){
            return true;
        }
        return false;
    }

}

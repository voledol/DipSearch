package project;

import java.util.HashMap;

public class PageDuplicateCheck {
    public static HashMap<String, Integer> existPages = new HashMap<String, Integer>();

    public  Boolean isNoneExistPage (String url){
        if((existPages.containsKey(url)&&(existPages.get(url)==1))){
            return true;
        }
        return false;
    }

}

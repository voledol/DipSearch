import model.Page;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
public class Main {
    public static String siteURL = "https://dimonvideo.ru/";

    public static void main(String[] args){
       // Nodelink rootUrl = new Nodelink(siteURL);
        //Set<Nodelink> result = new ForkJoinPool().invoke(new SiteMapper(rootUrl));
        //TreeSet<Nodelink> resultSet = new TreeSet<>(result);
        Page page = new Page();
        page.setCode(200);
        page.setContent("gjkexbkjcm");
        page.setPath("https://dimonvideo.ru/");
    }
}





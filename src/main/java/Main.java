import model.Index;
import model.Lemma;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
    public static String siteURL = "https://dimonvideo.ru/";

    public static void main(String[] args){
       // Nodelink rootUrl = new Nodelink(siteURL);
        //Set<Nodelink> result = new ForkJoinPool().invoke(new SiteMapper(rootUrl));
        //TreeSet<Nodelink> resultSet = new TreeSet<>(result);

//        try {
//            Indexation.insertPages(Indexation.takeUrls(siteURL));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Indexation.indexation();



//        Lemma lemma = Hibernate.getLemma();
//        System.out.println(lemma);

    }
}





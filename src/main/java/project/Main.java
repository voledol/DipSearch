package project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//import project.controllers.HibernateController;
import project.model.Site;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
/**Класс project.Main. запуск и работа приложения
 * @autor VG
 * @version 0.1
 * **/
public class Main {
    /** Поле класса project.PropertyLoader {@link PropertyLoader}*/
    public static PropertyLoader propertyes = new PropertyLoader();
//    /**Поле класса HibernateController {@link HibernateController} */
//    public static HibernateController hibernateController = new HibernateController();
    /**Поле sessionHibernate*/
    public static Session sessionHibernate;
    /**Поле создания класса соединения с сайтом{@link SiteConnect}*/
    public static SiteConnect connect = new SiteConnect();
    /**Поле подсчета добавленных в БД сайтов для индексации и поиска*/
    public static List<Site> allowedSitesList = new ArrayList<>();
    /***/
    public static void main(String[] args){
       propertyes = applicationReader();
//       sessionHibernate = hibernateController.getSessionFactory().openSession();
       SpringApplication.run(Main.class, args);
    }
    /**Функция получения конфигурационных параметров из файла application.yml и добавления списка сайтов в БД
     * @return возвращает заполненный параметрами объект класса project.PropertyLoader {@link PropertyLoader}*/
    public static PropertyLoader applicationReader (){
      ObjectMapper  mapper = new ObjectMapper(new YAMLFactory());
      mapper.findAndRegisterModules();
        try {
            propertyes = mapper.readValue(new File("C:\\Users\\voled\\IdeaProjects\\DipSearch\\application.yaml"), PropertyLoader.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(project.PropertyLoader.Site st: propertyes.getAvalibleSites().getAvailableSites()){
//            Site site = new Site();
//            site.setUrl(st.getUrl());
//            site.setName(st.getName());
//            site.setStatus(SiteStatus.FAILED);
//            siteCRUD.create(site);
//            sitesCount++;
//        }
        return propertyes;
    }
}





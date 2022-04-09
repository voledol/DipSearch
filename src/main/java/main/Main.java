package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//import connections.dataBase.SiteCRUD;
import connections.dataBase.SiteController;
import connections.sites.SiteConnect;
import model.Site;
import model.SiteStatus;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
/**Класс Main. запуск и работа приложения
 * @autor VG
 * @version 0.1
 * **/
public class Main {
    /** Поле класса PropertyLoader {@link PropertyLoader}*/
    public static PropertyLoader propertyes = new PropertyLoader();
    /**Поле класса HibernateController {@link HibernateController} */
    public static HibernateController hibernateController = new HibernateController();
    /**Поле создания класса SiteController {@link connections.dataBase.SiteController}*/
    public static SiteController siteCRUD = new SiteController();
    /**Поле sessionHibernate*/
    public static Session sessionHibernate;
    /**Поле создания класса соединения с сайтом{@link SiteConnect}*/
    public static SiteConnect connect = new SiteConnect();
    /**Поле подсчета добавленных в БД сайтов для индексации и поиска*/
    public static int sitesCount = 0;
    /***/
    public static void main(String[] args){
        sessionHibernate = hibernateController.getSessionFactory().openSession();
       propertyes = aplicationReader();
       SpringApplication.run(Main.class, args);
    }
    /**Функция получения конфигурационных параметров из файла application.yml и добавления списка сайтов в БД
     * @return возвращает заполненный параметрами объект класса PropertyLoader {@link PropertyLoader}*/
    public static PropertyLoader aplicationReader(){
      ObjectMapper  mapper = new ObjectMapper(new YAMLFactory());
      mapper.findAndRegisterModules();
        try {
            propertyes = mapper.readValue(new File("C:\\Users\\voled\\IdeaProjects\\DipSearch\\application.yaml"), PropertyLoader.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(PropertyLoader.Site st: propertyes.getAvalibleSites().getAvailableSites()){
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





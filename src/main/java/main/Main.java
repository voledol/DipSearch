package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//import connections.dataBase.SiteCRUD;
import connections.sites.SiteConnect;
import model.Site;
import model.SiteStatus;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication

public class Main {
    public static PropertyLoader propertyes = new PropertyLoader();
    public static HibernateController hibernateController = new HibernateController();
//    public static SiteCRUD siteCRUD = new SiteCRUD();
    public static Session sessionHibernate;
    public static SiteConnect connect = new SiteConnect();
    public static int sitesCount = 0;
    public static void main(String[] args){
        sessionHibernate = hibernateController.getSessionFactory().openSession();
       propertyes = aplicationReader();
       SpringApplication.run(Main.class, args);
    }
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





package project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
//import project.controllers.HibernateController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.model.Site;
import project.model.SiteStatus;
import project.services.SiteConnectService;

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
    /**Поле создания класса соединения с сайтом{@link SiteConnectService}*/
    public static List<Site> availableSites = new ArrayList<>();
    public static Boolean isIndexationRunning = false;
    public static void main(String[] args){
       propertyes = readApplication();
       SpringApplication.run(Main.class, args);
    }
    /**Функция получения конфигурационных параметров из файла application.yml и добавления списка сайтов в БД
     * @return возвращает заполненный параметрами объект класса project.PropertyLoader {@link PropertyLoader}*/
    public static PropertyLoader readApplication (){
      ObjectMapper  mapper = new ObjectMapper(new YAMLFactory());
      mapper.findAndRegisterModules();
        try {
            propertyes = mapper.readValue(new File("C:\\Users\\voled\\IdeaProjects\\DipSearch\\application.yaml"), PropertyLoader.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(project.PropertyLoader.Site st: propertyes.getAvailableSites().getAvailableSites()){
            Site site = new Site();
            site.setUrl(st.getUrl());
            site.setName(st.getName());
            site.setStatus(SiteStatus.FAILED.toString());
            availableSites.add(site);
        }
        return propertyes;
    }
}





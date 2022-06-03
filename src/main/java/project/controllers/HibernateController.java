package project.controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import project.model.*;


/**
 * Класс соединения с БД с помощью Hibernate
 * @author VG
 * @version 0.1
 * **/
public class HibernateController {
    /**Поле конфигурацции*/
    public  Configuration cofiguration = new org.hibernate.cfg.Configuration();
    /** поле регистрации файла конфигурации
     * */
    public  StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml").build();
    /**Поле metadata*/
    public  Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    /**Поле sessionFactory*/
    public  SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
    /**Поле сессии */
    public Session session = sessionFactory.openSession();

    /**Функция получения конфигурации Hibernate из файла конфигурации
      * @return возвращает sessionFactory подключения Hibernate
     */
    public  SessionFactory getSessionFactory(){
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(setConfigurationPropertiesFromFile()
                        .getProperties());
                sessionFactory = setConfigurationPropertiesFromFile().buildSessionFactory(builder.build());
        return sessionFactory;
    }
    public Configuration setConfigurationPropertiesFromFile(){
        Configuration configuration = new Configuration().configure();
        configuration.configure();
        configuration.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("connection.url", "jdbc:mysql://127.0.0.1:3306/search_engine?useSSL=false&serverTimezone=UTC");
        configuration.setProperty("connection.password", "Gorila123");
        configuration.setProperty("connection.username", "valgall");
        configuration.setProperty("connection.pool-size", "20");
        configuration.setProperty("dialect","org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty("show_sql", "true");
        configuration.setProperty("current_session_context_class", "thread");
        configuration.setProperty("hbm2ddl.auto", "validate");
        configuration.addAnnotatedClass(Field.class);
        configuration.addAnnotatedClass(Index.class);
        configuration.addAnnotatedClass(Lemma.class);
        configuration.addAnnotatedClass(Page.class);
        configuration.addAnnotatedClass(Site.class);
        return configuration;

    }
}

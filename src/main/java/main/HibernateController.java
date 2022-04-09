package main;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * Класс соединения с БД с помощью Hibernate
 * @autor VG
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
    public  Session session = sessionFactory.openSession();

    /**Функция получения конфигурации Hibernate из файла конфигурации
     *
      * @return возвращает sessionFactory подключения Hibernate
     * возможно исключение при неверных параметрах файла конфигурации
     */
    public  SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("connection.url", Main.propertyes.getSpring().getDatasource().getUrl());
                configuration.setProperty("connection.password", Main.propertyes.getSpring().getDatasource().getPassword());
                configuration.setProperty("connection.username", Main.propertyes.getSpring().getDatasource().getUsername());
                configuration.setProperty("connection.pool-size", "20");
                configuration.setProperty("dialect","org.hibernate.dialect.MySQL8Dialect");
                configuration.setProperty("show_sql", "true");
                configuration.setProperty("current_session_context_class", "thread");
                configuration.setProperty("hbm2ddl.auto", Main.propertyes.getSpring().getJpa().getHibernate().getDdl_auto());
                configuration.addAnnotatedClass(Field.class);
                configuration.addAnnotatedClass(Index.class);
                configuration.addAnnotatedClass(Lemma.class);
                configuration.addAnnotatedClass(Page.class);
                configuration.addAnnotatedClass(Site.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}

import model.Lemma;
import model.Page;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Hibernate {
    public static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml").build();
    public static Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    public static SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
    public static void save(Object obj){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(obj);
        transaction.commit();
        //sessionFactory.close();
    }
    public static List<Page> getPage(){
        Session session = sessionFactory.openSession();
        String sql = "From " + Page.class.getSimpleName();

        List<Page> pages = session.createQuery(sql).list();

        return pages;
    }
    public static Lemma updateLemma(String lemma){
        Session session = sessionFactory.openSession();
        String sql = "From " + Lemma.class.getSimpleName()+" Where lemma = '"+lemma+"'";
        List<Lemma> lemmas = session.createQuery(sql).setMaxResults(1).list();
        int frequency = lemmas.get(0).getFrequency() + 1;
            Transaction transaction = session.beginTransaction();
            String sqlUPdate = "UPDATE  " + Lemma.class.getSimpleName()+" set frequency = "+frequency + " Where lemma = '" + lemma +"'";
            session.createQuery(sqlUPdate).executeUpdate();
            transaction.commit();
            return lemmas.get(0);

    }
    public static boolean findLemm(String lemma){
        Session session = sessionFactory.openSession();
        String sql = "From " + Lemma.class.getSimpleName()+" Where lemma = '"+lemma+"'";
        List<Lemma> lemmas = session.createQuery(sql).setMaxResults(1).list();
        if(lemmas.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    public static Lemma findLemma(String lemma) {
        Session session = sessionFactory.openSession();
        String sql = "From " + Lemma.class.getSimpleName() + " Where lemma = '" + lemma + "'";
        List<Lemma> lemmas = session.createQuery(sql).setMaxResults(1).list();
        return lemmas.get(0);
    }
}

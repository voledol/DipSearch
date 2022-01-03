import model.Index;
import model.Lemma;
import model.Page;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hibernate {
    public static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml").build();
    public static Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    public static SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
    public static Session session = sessionFactory.openSession();
    public static void save(Object obj){
        Transaction transaction = session.beginTransaction();

        session.save(obj);
        transaction.commit();
    }
    public static List<Page> getPage(){

        String sql = "From " + Page.class.getSimpleName();

        List<Page> pages = session.createQuery(sql).list();

        return pages;
    }
    public static Lemma updateLemma(String lemma){

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

        String sql = "From " + Lemma.class.getSimpleName() + " Where lemma = '" + lemma + "'";
        List<Lemma> lemmas = session.createQuery(sql).setMaxResults(1).list();
        if(lemmas.isEmpty()){
            return new Lemma();

        }
        else{
            return lemmas.get(0);
        }
    }
    public static void closeSession(){
        sessionFactory.close();
    }


    public static List<Lemma> findLemmaList(HashMap<String, Integer> searchLems){
        List<Lemma> lems = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: searchLems.entrySet()){
            Lemma lemm = Hibernate.findLemma(entry.getKey());
            if (lemm!=null & (lemm.getFrequency()<200)){
                lems.add(lemm);
            }
        }
        return lems;
    }
    public static List<Index> findPageList(Lemma lemma){// если это заработает будет чудо
        String sql =  "From " + Index.class.getSimpleName() + " where lemma_id = " +lemma.getId();
        List<Index> indexes = session.createQuery(sql).list();
        return indexes;
    }
    public static List<Index> removePages(Lemma lemma, List<Index> indexList){// если это заработает будет чудо
        List<Index> finPages = new ArrayList<>();
        // вот этот кусок мне не нравится
        for(Index index: indexList){
            String sql1 = "From " + Index.class.getSimpleName() + " where page_id = " +index.getPage_id();
            List<Index> pagesId = session.createQuery(sql1).list();
            for(Index page: pagesId){
                if(page.getLemma_id()!= lemma.getId()){
                    continue;
                }
                else{
                    finPages.add(page);
                }
            }
        }
        return finPages;
    }
//    public static float relevant(int page_id, int lemm_id ){
//      float  relevant = 0;
//      String sql = "From " + Index.class.getSimpleName() + " where page_id = " + page_id + "and lemm_id = " +lemm_id;
//      session.createQuery(sql).setMaxResults(1).list().get(0).getRank;
//      return relevant;
//    }
}

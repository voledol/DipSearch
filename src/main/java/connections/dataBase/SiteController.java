package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Index;
import model.Lemma;
import model.Site;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiteController implements DBRepository<Site> {
    @Override
    public void add(Site entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
    }

    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }

    @Override
    public void update(Site entity) {
        Main.sessionHibernate.update(entity);
    }

    @Override
    public Site get(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Main.sessionHibernate.createQuery(query).getSingleResult();
        return Main.sessionHibernate.createQuery(query).getSingleResult();
    }

    @Override
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
    public List<Lemma> findLemmaList(HashMap<String, Integer> searchString){
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Lemma> query = builder.createQuery(Lemma.class);
        Root<Lemma> root = query.from(Lemma.class);

        List<Lemma> resultSet = new ArrayList<>();
        for(Map.Entry entry: searchString.entrySet() ){
            query.select(root).where(builder.like(root.get("lemma"), entry.getKey().toString()));
            Lemma lem = Main.sessionHibernate.createQuery(query).getSingleResult();
            resultSet.add(lem);
        }
        return resultSet;
    }
    public List<Index> findIndexList(String column, String value){
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Index> query = builder.createQuery(Index.class);
        Root<Index> root = query.from(Index.class);
        List<Index> resultSet = new ArrayList<>();
        query.select(root).where(builder.like(root.get(column),value));
        List<Index> indexList = Main.sessionHibernate.createQuery(query).getResultList();
        return resultSet;
    }
    public List<Index> findPageList(Lemma lemma) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Index> query = builder.createQuery(Index.class);
        Root<Index> root = query.from(Index.class);
        List<Index> resultSet = new ArrayList<>();
        query.select(root).where(builder.like(root.get("lemma_id"), String.valueOf(lemma.getId())));
        List<Index> indexList = Main.sessionHibernate.createQuery(query).getResultList();
        return resultSet;
    }
}

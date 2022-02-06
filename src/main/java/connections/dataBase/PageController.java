package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Page;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PageController implements DBRepository<Page>{
    @Override
    public void add(Page entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();

    }

    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }

    @Override
    public void update(Page entity) {
        Main.sessionHibernate.update(entity);
    }
    @Override
    public Page get(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
            CriteriaQuery<Page> query = builder.createQuery(Page.class);
            Root<Page> root = query.from(Page.class);
            query.select(root).where(builder.like(root.get(criteria1), criteria2));
             Main.sessionHibernate.createQuery(query).getSingleResult();
            return Main.sessionHibernate.createQuery(query).getSingleResult();
    }
    @Override
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

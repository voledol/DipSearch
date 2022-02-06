package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Lemma;
import model.Page;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LemmaController implements DBRepository<Lemma> {
    @Override
    public void add(Lemma entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
    }

    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }

    @Override
    public void update(Lemma entity) {
        Main.sessionHibernate.update(entity);
    }

    @Override
    public Lemma get(String criteria1, String criteria2) throws NullPointerException {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Lemma> query = builder.createQuery(Lemma.class);
        Root<Lemma> root = query.from(Lemma.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Main.sessionHibernate.createQuery(query).getSingleResult();
        return Main.sessionHibernate.createQuery(query).getSingleResult();
    }

    @Override
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

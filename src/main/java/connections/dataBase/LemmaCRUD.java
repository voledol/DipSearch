package connections.dataBase;

import main.HibernateController;
import main.Main;
import model.Lemma;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LemmaCRUD extends CRUD {



    @Override
    public Object read(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Lemma> query = builder.createQuery(Lemma.class);
        Root<Lemma> root = query.from(Lemma.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Lemma lemma = Main.sessionHibernate.createQuery(query).getSingleResult();
        return lemma;
    }
}

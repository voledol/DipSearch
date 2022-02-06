package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Index;
import model.Page;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class IndexController implements DBRepository<Index> {
    @Override
    public void add(Index entity) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void update(Index entity) {

    }

    @Override
    public Index get(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Index> query = builder.createQuery(Index.class);
        Root<Index> root = query.from(Index.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Main.sessionHibernate.createQuery(query).getSingleResult();
        return Main.sessionHibernate.createQuery(query).getSingleResult();
    }

    @Override
    public boolean exists(String id) {
        return false;
    }
}

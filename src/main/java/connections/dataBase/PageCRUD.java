package connections.dataBase;

import main.HibernateController;
import main.Main;
import model.Page;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PageCRUD extends CRUD{
    @Override
    public Object read(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Page> query = builder.createQuery(Page.class);
        Root<Page> root = query.from(Page.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Page page = Main.sessionHibernate.createQuery(query).getSingleResult();
        return page;
    }
}

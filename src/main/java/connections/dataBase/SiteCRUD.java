package connections.dataBase;

import main.HibernateController;
import main.Main;
import model.Site;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SiteCRUD  extends CRUD{


    @Override
    public Object read(String criteria1,String criteria2 ) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Site site = Main.sessionHibernate.createQuery(query).getSingleResult();
        return site;
    }


}

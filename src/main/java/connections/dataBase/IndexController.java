package connections.dataBase;

import Interfaces.DBRepository;
import main.HibernateController;
import main.Main;
import model.Index;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 * Class controller for Index table in DB
 * @author VG
 * @version 0.1
 * **/
public class IndexController implements DBRepository<Index> {
    /**
     * Function add object in DB{@link Index}
     * @param entity - entity DB\Object
     */
    @Override
    @Transactional
    public void add(Index entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
        Main.sessionHibernate.clear();
    }
    /**
     * Function deleted Object from DB  {@link Index}
     * @param id - object Id
     */
    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }
    /**
     * Function updated object in DB {@link Index}
     * @param entity - object Index
     */
    @Override
    public void update(Index entity) {
        Main.sessionHibernate.update(entity);
    }
    /**
     * Function get object from {@link Index}
     * @param criteria1 - object key
     * @param criteria2 -  value
     * @return object Index with given parameters, if it exist.
     * return empty object if it don't exist
     * */
    @Override
    public Index get(String criteria1, String criteria2) throws NullPointerException{
        Index resIndex = new Index();
        try{
            CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
            CriteriaQuery<Index> query = builder.createQuery(Index.class);
            Root<Index> root = query.from(Index.class);
            query.select(root).where(builder.like(root.get(criteria1), criteria2));
           resIndex = Main.sessionHibernate.createQuery(query).getSingleResult();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resIndex;
    }
    /**
     * Function check exist object {@link Index} in DB
     * @param id - object key
     * @return true if object exist in DB, false if it don't exist
     * */
    @Override
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

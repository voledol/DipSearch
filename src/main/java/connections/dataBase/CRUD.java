package connections.dataBase;

import interfaсes.DBCRUD;
import main.HibernateController;
import main.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class CRUD implements DBCRUD {
    @Override
    public void create(Object o) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(o);
        transaction.commit();
    }

    @Override
    public Object read(String criteria1, String criteria2) {
        Object o =  new Object();
        return o;
    }

    @Override
    public void update(Object o) {
        Main.sessionHibernate.update(o);
    }

    @Override
    public void delete(Object o) {
        Main.sessionHibernate.delete(o);
    }
}

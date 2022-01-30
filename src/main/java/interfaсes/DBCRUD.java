package interfaсes;

import org.hibernate.Session;

public interface DBCRUD {

     void create(Object o);
    Object read(String criteria1, String criteria2);
    void update(Object o);
    void delete(Object o);
}

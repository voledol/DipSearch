package interfaсes;
public interface DBRepository<T> {
    void add(T entity);

    void delete(String id);

    void update(T entity);

    T get(String criteria1, String criteria2);
    boolean exists(String id);

}

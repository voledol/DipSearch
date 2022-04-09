package interfaсes;

import model.Site;

/** Интерфейс для работы с базой данных
 * @autor VG
 * @version 0.1
  **/
public interface DBRepository<T> {
    /** Добавление объекта в БД
     * @param entity - сущность БД/объект
     * **/
    void add(T entity);
    /**
     * Удаление объекта из БД по id
     * */
    void delete(String id);
    /** Обновление объекта в БД
     * @param entity - сущность БД/объект
     * **/
    void update(T entity);
    /**
 * Функция получения объекта
 * @param criteria1 - ключ объекта
 * @param criteria2 - значение
     * @return возвращает заданный в классе наследнике объект
     * */
    T get(String criteria1, String criteria2);
    /**
     * Функция проверки наличия объекта {@link Site} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    boolean exists(String id);

}

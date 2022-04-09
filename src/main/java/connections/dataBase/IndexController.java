package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Index;
import org.hibernate.Transaction;
/**
 * Класс контроллер для работы с таблицей Index в БД
 * @autor VG
 * @version 0.1
 * **/

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class IndexController implements DBRepository<Index> {
    @Override
    /**
     * Функция получения добавления объекта {@link Index}
     */
    public void add(Index entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
    }

    @Override
    /**
     * Функция удаления объекта {@link Index}
     * @param id - id объекта
     */
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }

    @Override
    /**
     * Функция обновления объекта {@link Index}
     * @param entity - объект Index
     */
    public void update(Index entity) {
        Main.sessionHibernate.update(entity);
    }

    @Override
    /**
     * Функция получения объекта {@link Index}
     * @param criteria1 - ключ объекта
     * @param criteria2 - значение
     * @return возваращает объект Index с заданными параметрами если он сущетвует,
     * возваращает пустой объект если  не найден объект с заданными параметрами в БД
     * */
    public Index get(String criteria1, String criteria2) throws NullPointerException{
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Index> query = builder.createQuery(Index.class);
        Root<Index> root = query.from(Index.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Main.sessionHibernate.createQuery(query).getSingleResult();
        return Main.sessionHibernate.createQuery(query).getSingleResult();
    }

    @Override
    /**
     * Функция проверки наличия объекта {@link Index} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

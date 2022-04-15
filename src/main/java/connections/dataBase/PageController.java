package connections.dataBase;

import Interfaces.DBRepository;
import main.Main;
import model.Page;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
/**
 * Класс контроллер для работы с таблицей Page в БД
 * @author VG
 * @version 0.1
 * **/
public class PageController implements DBRepository<Page>{
    /**
     * Функция получения добавления объекта {@link Page}
     */
    @Override
    public void add(Page entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();

    }
    /**
     * Функция удаления объекта {@link Page}
     */
    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }
    /**
     * Функция обновления объекта {@link Page}
     * @param entity - объект Index
     */
    @Override
    public void update(Page entity) {
        Main.sessionHibernate.update(entity);
    }
    /**
     * Функция получения объекта {@link Page}
     * @param criteria1 - ключ объекта
     * @param criteria2 - значение
     * @return возваращает объект Page с заданными параметрами если он сущетвует,
     * возваращает пустой объект если  не найден объект с заданными параметрами в БД
     * */
    @Override
    public Page get(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
            CriteriaQuery<Page> query = builder.createQuery(Page.class);
            Root<Page> root = query.from(Page.class);
            query.select(root).where(builder.like(root.get(criteria1), criteria2));
             Main.sessionHibernate.createQuery(query).getSingleResult();
            return Main.sessionHibernate.createQuery(query).getSingleResult();
    }
    /**
     * Функция проверки наличия объекта {@link Page} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    @Override
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Index;
import model.Lemma;
import model.Page;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
/**
 * Класс контроллер для работы с таблицей Page в БД
 * @autor VG
 * @version 0.1
 * **/
public class PageController implements DBRepository<Page>{
    @Override
    /**
     * Функция получения добавления объекта {@link Page}
     */
    public void add(Page entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();

    }

    @Override
    /**
     * Функция удаления объекта {@link Page}
     */
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }

    @Override
    /**
     * Функция обновления объекта {@link Page}
     * @param entity - объект Index
     */
    public void update(Page entity) {
        Main.sessionHibernate.update(entity);
    }
    @Override
    /**
     * Функция получения объекта {@link Page}
     * @param criteria1 - ключ объекта
     * @param criteria2 - значение
     * @return возваращает объект Page с заданными параметрами если он сущетвует,
     * возваращает пустой объект если  не найден объект с заданными параметрами в БД
     * */
    public Page get(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
            CriteriaQuery<Page> query = builder.createQuery(Page.class);
            Root<Page> root = query.from(Page.class);
            query.select(root).where(builder.like(root.get(criteria1), criteria2));
             Main.sessionHibernate.createQuery(query).getSingleResult();
            return Main.sessionHibernate.createQuery(query).getSingleResult();
    }
    @Override
    /**
     * Функция проверки наличия объекта {@link Page} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

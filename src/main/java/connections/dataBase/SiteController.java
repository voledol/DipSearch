package connections.dataBase;

import interfaсes.DBRepository;
import main.Main;
import model.Index;
import model.Lemma;
import model.Page;
import model.Site;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Класс контроллер для работы с таблицей Site в БД
 * @autor VG
 * @version 0.1
 * **/
public class SiteController implements DBRepository<Site> {
    /**
     * Функция получения добавления объекта {@link Site}
     */
    @Override
    public void add(Site entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
    }
    /**
     * Функция удаления объекта {@link Site}
     */
    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }
    /**
     * Функция обновления объекта {@link Site}
     * @param entity - объект Index
     */
    @Override
    public void update(Site entity) {
        Main.sessionHibernate.update(entity);
    }
    /**
     * Функция получения объекта {@link Site}
     * @param criteria1 - ключ объекта
     * @param criteria2 - значение
     * @return возваращает объект Site с заданными параметрами если он сущетвует,
     * возваращает пустой объект если  не найден объект с заданными параметрами в БД
     * */
    @Override
    public Site get(String criteria1, String criteria2) {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Site> query = builder.createQuery(Site.class);
        Root<Site> root = query.from(Site.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Main.sessionHibernate.createQuery(query).getSingleResult();
        return Main.sessionHibernate.createQuery(query).getSingleResult();
    }

    @Override
    /**
     * Функция проверки наличия объекта {@link Site} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

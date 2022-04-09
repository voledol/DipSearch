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

/**
 * Класс контроллер для работы с таблицей lemma в БД
 * @autor VG
 * @version 0.1
 * **/
public class LemmaController implements DBRepository<Lemma> {
    @Override
    /**
     * Функция получения добавления объекта {@link Lemma}
     */
    public void add(Lemma entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
    }
    /**
     * Функция удаления объекта {@link Lemma}
     */
    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }

    @Override
    /**
     * Функция обновления объекта {@link Lemma}
     * @param entity - объект Index
     */
    public void update(Lemma entity) {
        Main.sessionHibernate.update(entity);
    }

    @Override
    /**
     * Функция получения объекта {@link Lemma}
     * @param criteria1 - ключ объекта
     * @param criteria2 - значение
     * @return возваращает объект Lemma с заданными параметрами если он сущетвует,
     * возваращает пустой объект если  не найден объект с заданными параметрами в БД
     * */
    public Lemma get(String criteria1, String criteria2) throws NullPointerException {
        CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
        CriteriaQuery<Lemma> query = builder.createQuery(Lemma.class);
        Root<Lemma> root = query.from(Lemma.class);
        query.select(root).where(builder.like(root.get(criteria1), criteria2));
        Main.sessionHibernate.createQuery(query).getSingleResult();
        return Main.sessionHibernate.createQuery(query).getSingleResult();
    }

    @Override
    /**
     * Функция проверки наличия объекта {@link Lemma} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }
}

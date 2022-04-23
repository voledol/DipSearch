package connections.dataBase;

import Interfaces.DBRepository;
import main.HibernateController;
import main.Main;
import main.PropertyLoader;
import model.Lemma;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Класс контроллер для работы с таблицей lemma в БД
 * @author VG
 * @version 0.1
 * **/
public class LemmaController implements DBRepository<Lemma> {
    /**
     * Функция получения добавления объекта {@link Lemma}
     */
    @Override
    @Transactional
    public void add(Lemma entity) {
        Transaction transaction = Main.sessionHibernate.beginTransaction();
        Main.sessionHibernate.save(entity);
        transaction.commit();
    }
    /**
     * Функция удаления объекта {@link Lemma}
     * @param id - id объекта
     */
    @Override
    public void delete(String id) {
        Main.sessionHibernate.delete(id);
    }
    /**
     * Функция обновления объекта {@link Lemma}
     * @param entity - объект Index
     */
    @Override
    public void update(Lemma entity) {
        Main.sessionHibernate.update(entity);
    }
    /**
     * Функция получения объекта {@link Lemma}
     * @param criteria1 - ключ объекта
     * @param criteria2 - значение
     * @return возваращает объект Lemma с заданными параметрами если он сущетвует,
     * возваращает пустой объект если  не найден объект с заданными параметрами в БД
     * */
    @Override
    public Lemma get(String criteria1, String criteria2) throws NullPointerException {
        Lemma resLemm = new Lemma();
        try{
            CriteriaBuilder builder = Main.sessionHibernate.getCriteriaBuilder();
            CriteriaQuery<Lemma> query = builder.createQuery(Lemma.class);
            Root<Lemma> root = query.from(Lemma.class);
            query.select(root).where(builder.like(root.get(criteria1), criteria2));
            resLemm = Main.sessionHibernate.createQuery(query).getSingleResult();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return resLemm;
    }
    /**
     * Функция проверки наличия объекта {@link Lemma} в БД
     * @param id - ключ объекта
     * @return возвращает true если объект есть в БД, false если объекта нет.
     * */
    @Override
    public boolean exists(String id) {
        return Main.sessionHibernate.contains(id);
    }

    /** Функция подсчета количества лемм соотвествующих конкретному сайту
     * @param id id сайта
     * @return возвращает количество лемм по id сайта
     */
    public int getLemsCount (int id){
        String hql = "FROM Lemma where site_id = :paramName";
        Query query = Main.sessionHibernate.createQuery(hql);
        query.setParameter("paramName", id);
        List users = query.getResultList();
        return users.size();
    }
}

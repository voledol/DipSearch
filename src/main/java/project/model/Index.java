package project.model;

import javax.persistence.*;

/**
 * Класс описывающий сущность Index
 *
 * @author VG
 * @version 0.1
 **/
@Entity
@Table (name = "indexeslist")
public class Index {
    /**
     * Поле ID
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    public int id;
    /**
     * Поле ID страницы
     */
    @Column (name = "page_id")
    public int pageid;
    /**
     * Поле Id леммы
     */
    @Column (name = "lemma_id")
    public int lemmaid;
    /**
     * Поле ранга леммы
     */
    @Column (name = "lemma_rank")
    public float rank;

    /**
     * Getters and setters
     */
    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public float getRank () {
        return rank;
    }

    public void setRank (float rank) {
        this.rank = rank;
    }

    public int getPageid () {
        return pageid;
    }

    public void setPageid (int pageid) {
        this.pageid = pageid;
    }

    public int getLemmaid () {
        return lemmaid;
    }

    public void setLemmaid (int lemmaid) {
        this.lemmaid = lemmaid;
    }
}

package model;

import javax.persistence.*;
/**Класс описывающий сущность Index
 * @author VG
 * @version 0.1
 * **/
@Entity
@Table (name = "indexeslist")
public class Index  {
    /**Поле ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public int id;
    /**Поле ID страницы*/
    @Column (name = "page_id")
    public int  page_id;
    /**Поле Id леммы */
    @Column (name = "lemma_id")
    public int lemma_id;
    /**Поле ранга леммы*/
    @Column (name = "lemma_rank")
    public float rank;
    /**Getters and setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public int getLemma_id() {
        return lemma_id;
    }

    public void setLemma_id(int lemma_id) {
        this.lemma_id = lemma_id;
    }
}

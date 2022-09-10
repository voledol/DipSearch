package project.model;

import javax.persistence.*;
import java.util.Objects;

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
    public int pageId;
    /**
     * Поле Id леммы
     */
    @Column (name = "lemma_id")
    public int lemmaId;
    @Column (name = "site_id")
    public int siteId;
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

    public int getPageId () {
        return pageId;
    }

    public void setPageId (int pageId) {
        this.pageId = pageId;
    }

    public int getLemmaId () {
        return lemmaId;
    }

    public void setLemmaId (int lemmaId) {
        this.lemmaId = lemmaId;
    }

    public int getSiteId () {
        return siteId;
    }

    public void setSiteId (int siteId) {
        this.siteId = siteId;
    }

    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Index)) return false;
        Index index = (Index) o;
        return getId() == index.getId() && getPageId() == index.getPageId() && getLemmaId() == index.getLemmaId() && Float.compare(index.getRank(), getRank()) == 0;
    }

    @Override
    public int hashCode () {
        return Objects.hash(getId(), getPageId(), getLemmaId(), getRank());
    }

    public String toString () {
        return "id :" + getId() + "\n" +
                "pageId :" + getPageId() + "\n" +
                "lemmaId :" + getLemmaId() + "\n" +
                "rank :" + getRank() + "\n" +
                "siteId :" + getSiteId() + "\n";
    }
}

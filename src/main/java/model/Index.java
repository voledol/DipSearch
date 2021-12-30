package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "indexeslist")
public class Index  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    @Column (name = "page_id")
    public int  page_id;
    @Column (name = "lemma_id")
    public int lemma_id;
    @Column (name = "lemma_rank")
    public float rank;

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

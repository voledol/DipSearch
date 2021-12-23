package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Index implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    @OneToOne
    @JoinColumn(name = "page_id")
    public Page page;
    @OneToOne
    @JoinColumn(name = "lemma_")
    public Lemma lemma;
    public float rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Lemma getLemma() {
        return lemma;
    }

    public void setLemma(Lemma lemma) {
        this.lemma = lemma;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }
}

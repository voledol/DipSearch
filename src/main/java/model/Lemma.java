package model;

import javax.persistence.*;

@Entity
@Table (name = "lemma")
public class Lemma implements Comparable<Lemma>{
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public int id;
    @Column (name = "lemma")
    public String lemma;
    @Column (name = "frequency")
    public int frequency;

    public int getId() {
        return id;
    }

    public String getLemma() {
        return lemma;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Lemma o) {
        return  o.getFrequency() - this.getFrequency();
    }

}

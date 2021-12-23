package model;

import javax.persistence.*;

@Entity
public class Lemma {
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
}

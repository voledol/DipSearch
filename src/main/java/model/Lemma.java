package model;

import javax.persistence.*;
/**Класс описывающий сущность Lemma
 * @autor VG
 * @version 0.1
 * **/
@Entity
@Table (name = "lemma")
public class Lemma implements Comparable<Lemma>{
    /**Поле id*/
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public int id;
    /**Поле лемма*/
    @Column (name = "lemma")
    public String lemma;
    /**Поле частоты*/
    @Column (name = "frequency")
    public int frequency;
    /**Поле id сайта*/
    public int site_id;
    /**getters and setters*/
    public int getSite_id() {return site_id;}
    public void setSite_id(int site_id) {this.site_id = site_id;}

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

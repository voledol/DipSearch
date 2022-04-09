package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**Класс описывающий сущность Field
 * @autor VG
 * @version 0.1
 * **/
public class Field {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    /**Поле id*/
    public int id;
    /**Поле имя */
    public String name;
    /**Поле селектор*/
    public String selector;
    /**Поле вес*/
    public float weight;
    /**Getters and setters*/
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }

    public float getWeight() {
        return weight;
    }
}

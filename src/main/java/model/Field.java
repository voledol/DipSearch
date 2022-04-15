package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**Класс описывающий сущность Field
 * @author VG
 * @version 0.1
  **/
@Entity
public class Field {
    /**Поле id*/
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
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

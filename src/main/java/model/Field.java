package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Field {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public int id;
    public String name;
    public String selector;
    public float weight;

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

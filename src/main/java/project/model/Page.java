package project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс описывающий сущность Page
 *
 * @author VG
 * @version 0.1
 **/

@Entity
@Table (name = "page")
@Getter
@Setter
public class Page {
    /**
     * Поле id
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Поле путь
     */
    private String path;
    /**
     * Поле код ответа страницы
     */
    private int code;
    /**
     * поле контент на с ранице
     */
    @Column (columnDefinition = "mediumtext")
    private String content;
    /**
     * Поле id сайта
     */
    @Column (name = "site_id")
    private Integer siteId;

    /**
     * Конструктор Page
     */
    public Page () {
    }

    @Override
    public boolean equals (Object o) {
        Page page = (Page) o;
        return getPath().equals(page.getPath());
    }

    @Override
    public int hashCode () {
        return Objects.hash(path);
    }
}

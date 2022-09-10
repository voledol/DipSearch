package project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Класс описывающий сущность Site
 *
 * @author VG
 * @version 0.1
 **/
@Entity

@Table (name = "site")
@Getter
@Setter
public class Site {
    /**
     * Поле id
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Поле статуса сайта
     */
    private String status;
    /**
     * Поле времени последнего обновления статуса сайта
     */
    @Column (name = "status_time")
    private Date statusTime;
    /**
     * Поле последняя ошибка индексации
     */
    @Column (name = "last_error")
    private String lastError;
    /**
     * Поле адрес сайта
     */
    private String url;
    /**
     * Поле имя сайта
     */
    private String name;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return Objects.equals(url, site.url);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, status, statusTime, lastError, url, name);
    }
}

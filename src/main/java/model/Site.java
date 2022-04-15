package model;

import javax.persistence.*;
import java.sql.Date;
/**Класс описывающий сущность Site
 * @author VG
 * @version 0.1
 * **/
@Entity
@Table(name = "site")
public class Site {
    /**Поле id*/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    /**Поле статуса сайта*/
    private SiteStatus status;
    /**Поле времени последнего обновления статуса сайта*/
    private Date status_time;
    /**Поле последняя ошибка индексации*/
    private String last_error;
    /**Поле адрес сайта*/
    private String url;
    /**Поле имя сайта*/
    private String name;
    /**Getters and setters*/
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public SiteStatus getStatus() {return status;}
    public void setStatus(SiteStatus status) {this.status = status;}
    public Date getStatus_time() {return status_time;}
    public void setStatus_time(Date status_time) {this.status_time = status_time;}
    public String getLast_error() {return last_error;}
    public void setLast_error(String last_error) {this.last_error = last_error;}
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}

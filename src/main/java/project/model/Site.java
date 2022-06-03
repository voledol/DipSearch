package project.model;

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
    @Column (name = "status_time")
    private Date statusTime;
    /**Поле последняя ошибка индексации*/
    @Column (name = "last_error")
    private String lastError;
    /**Поле адрес сайта*/
    private String url;
    /**Поле имя сайта*/
    private String name;
    /**Getters and setters*/
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public SiteStatus getStatus() {return status;}
    public void setStatus(SiteStatus status) {this.status = status;}
    public Date getStatusTime () {return statusTime;}
    public void setStatusTime (Date statusTime) {this.statusTime = statusTime;}
    public String getLastError () {return lastError;}
    public void setLastError (String lastError) {this.lastError = lastError;}
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}

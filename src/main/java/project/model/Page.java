package project.model;

import javax.persistence.*;
/**Класс описывающий сущность Page
 * @author VG
 * @version 0.1
 * **/
@Entity
@Table (name = "page")
public class Page {
    /**Поле id*/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    /**Поле путь*/
    private String path;
    /**Поле код ответа страницы*/
    private int code;
    /**поле контент на с ранице*/
    private String content;
    /**Поле id сайта*/
    @Column (name = "site_id")
    private Integer siteId;
    /**Конструктор Page*/
    public Page(){
    }
    /**Getters and setters*/
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getSiteId () {return siteId;}
    public void setSiteId (int siteId) {this.siteId = siteId;}
}

package model;

import javax.persistence.*;
/**Класс описывающий сущность Page
 * @autor VG
 * @version 0.1
 * **/
@Entity
@Table (name = "page")
public class Page {
    /***/
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    /**Поле id*/
    private int id;
    /**Поле путь*/
    private String path;
    /**Поле код ответа страницы*/
    private int code;
    /**поле контент на с ранице*/
    private String content;
    /**Поле id сайта*/
    private int site_id;
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
    public int getSite_id() {return site_id;}
    public void setSite_id(int site_id) {this.site_id = site_id;}
}

package model;

import javax.persistence.*;

@Entity
@Table (name = "page")
public class Page {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String path;
    private int code;
    private String content;
    private int site_id;

    public Page(){
    }
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

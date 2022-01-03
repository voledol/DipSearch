package model;

import lombok.NoArgsConstructor;

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
}

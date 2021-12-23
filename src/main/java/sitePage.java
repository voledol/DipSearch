import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class sitePage {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public int id;
    public String path;
    public int code;
    public String content;

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
        this.path = path.replace(SiteMapper.getSite(),"");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content.replace("'", "\\s");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

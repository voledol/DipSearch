package project.model;

/**
 * Класс выдающий результат поискового запроса
 *
 * @author VG
 * @version 0.1
 **/
public class ResultPage {
    /**
     * Поле адреса страницы
     */
    String url;
    /**
     * Поле заголовка страницы
     */
    String title;
    /**
     * Поле сниппет
     */
    String snippet;
    /**
     * поле релевантность
     */
    float relevance;
    /**
     * поле id сайта
     */
    int site_id;


    /**
     * getters and setters
     */
    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle () {
    }

    public String getSnippet () {
        return snippet;
    }

    public void setSnippet (String snippet) {
        this.snippet = snippet;
    }

    public float getRelevance () {
        return relevance;
    }

    public void setRelevance (float relevance) {
        this.relevance = relevance;
    }

    public int getSite_id () {
        return site_id;
    }

    public void setSite_id (int site_id) {
        this.site_id = site_id;
    }
}

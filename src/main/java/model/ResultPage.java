package model;

import connections.sites.SiteConnect;

public class ResultPage {
    String url;
    String title;
    String snippet;
    float relevance;
    private SiteConnect siteConnect = new SiteConnect();


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        siteConnect.getConnection(url);
        this.title = siteConnect.getContent("title").toString();
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public float getRelevance() {
        return relevance;
    }

    public void setRelevance(float relevance) {
        this.relevance = relevance;
    }
}

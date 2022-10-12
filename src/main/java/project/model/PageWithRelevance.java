package project.model;

public class PageWithRelevance implements Comparable<PageWithRelevance> {
    public Double relevance;
    public Page page;


    public Double getRelevance () {
        return relevance;
    }

    public void setRelevance (Double relevance) {
        this.relevance = relevance;
    }

    public Page getPage () {
        return page;
    }

    public void setPage (Page page) {
        this.page = page;
    }

    @Override
    public int compareTo (PageWithRelevance o) {
        if (this.relevance > o.relevance) {
            return -1;
        }
        return 0;
    }
}

package main;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

public class PropertyLoader{


    private Spring spring;
    @JsonProperty("available-sites")
    private AvailableSites avalibleSites;

    public Spring getSpring() {
        return spring;
    }

    public void setSpring(Spring spring) {
        this.spring = spring;
    }

    public AvailableSites getAvalibleSites() {
        return avalibleSites;
    }

    public void setAvalibleSites(AvailableSites avalibleSites) {
        this.avalibleSites = avalibleSites;
    }

    public class Spring{
private Datasource datasource;
private JPA jpa;

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public JPA getJpa() {
        return jpa;
    }

    public void setJpa(JPA jpa) {
        this.jpa = jpa;
    }
}
public static class Datasource{

private String url;
private String username;
private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public static class JPA{
    private Hibernate hibernate;
    private String ddl_auto;

    public Hibernate getHibernate() {
        return hibernate;
    }

    public void setHibernate(Hibernate hibernate) {
        this.hibernate = hibernate;}
public static class Hibernate{
        @JsonProperty("ddl-auto")
    private String ddl_auto;

    public String getDdl_auto() {
        return ddl_auto;
    }

    public void setDdl_auto(String ddl_auto) {
        this.ddl_auto = ddl_auto;
    }
}

}
    public static class AvailableSites {
        @JsonProperty("sites")
        Site[] avalibleSites;

        public Site[] getAvailableSites() {
            return avalibleSites;
        }

        public void setAvailableSites(Site[] avalibleSites) {
            this.avalibleSites = avalibleSites;
        }
    }


    public static class Site{
        @JsonProperty("url")
        public String url;
        @JsonProperty("name")
        public String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

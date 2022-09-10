package project;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс получения свойст из файла application.yml
 *
 * @author VG
 * @version 0.1
 **/
public class PropertyLoader {


    /**
     * Поле настроек SpringBoot {@link Spring}
     */
    private Spring spring;
    /**
     * Поле AAvailableSites {@link AvailableSites}
     */
    @JsonProperty ("available-sites")
    private AvailableSites availableSites;

    /**
     * Getters and setters
     */
    public Spring getSpring () {
        return spring;
    }

    public void setSpring (Spring spring) {
        this.spring = spring;
    }

    public AvailableSites getAvailableSites () {
        return availableSites;
    }

    public void setAvailableSites (AvailableSites availableSites) {
        this.availableSites = availableSites;
    }

    /**
     * Класс настроек SpringBoot
     */
    public class Spring {
        /**
         * Поле datasource
         */
        private Datasource datasource;
        /**
         * Поле jpa {@link JPA}
         */
        private JPA jpa;

        /**
         * Getters and setters
         */
        public Datasource getDatasource () {
            return datasource;
        }

        public void setDatasource (Datasource datasource) {
            this.datasource = datasource;
        }

        public JPA getJpa () {
            return jpa;
        }

        public void setJpa (JPA jpa) {
            this.jpa = jpa;
        }
    }

    /**
     * Класс получения Datasource
     */
    public static class Datasource {
        /**
         * Поле адреса сервера
         */
        private String url;
        /**
         * Поле UserName
         */
        private String username;
        /**
         * Поле Password
         */
        private String password;

        /**
         * Getters and setters
         */
        public String getUrl () {
            return url;
        }

        public void setUrl (String url) {
            this.url = url;
        }

        public String getUsername () {
            return username;
        }

        public void setUsername (String username) {
            this.username = username;
        }

        public String getPassword () {
            return password;
        }

        public void setPassword (String password) {
            this.password = password;
        }
    }

    /**
     * Класс настроек JPA
     */
    public static class JPA {
        /**
         * Поле Hibernate {@link Hibernate}
         */
        private Hibernate hibernate;
        /**
         * Поле параметра ddl_auto
         */
        private String ddl_auto;

        /**
         * Getters and setters
         */
        public Hibernate getHibernate () {
            return hibernate;
        }

        public void setHibernate (Hibernate hibernate) {
            this.hibernate = hibernate;
        }

        /**
         * Класс настроек Hibernate
         */
        public static class Hibernate {
            /**
             * поле ddl_auto
             */
            @JsonProperty ("ddl-auto")
            private String ddl_auto;
            private String dialect;

            public String getDialect () {
                return dialect;
            }

            public void setDialect (String dialect) {
                this.dialect = dialect;
            }

            public String getDdl_auto () {
                return ddl_auto;
            }

            public void setDdl_auto (String ddl_auto) {
                this.ddl_auto = ddl_auto;
            }
        }


    }

    /**
     * Класс AvailableSites
     */
    public static class AvailableSites {
        /**
         * Поле доступных для индексации и поиска сайтов
         */
        @JsonProperty ("sites")
        Site[] availableSites;

        /**
         * Getters and setters
         */
        public Site[] getAvailableSites () {
            return availableSites;
        }

        public void setAvailableSites (Site[] avalibleSites) {
            this.availableSites = avalibleSites;
        }
    }

    /**
     * Класс описывающий сущность Site
     */
    public static class Site {
        /**
         * Поле адреса сайта
         */
        @JsonProperty ("url")
        public String url;
        /**
         * Поле имени сайта
         */
        @JsonProperty ("name")
        public String name;

        /**
         * Getters and setters
         */
        public String getUrl () {
            return url;
        }

        public void setUrl (String url) {
            this.url = url;
        }

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }
    }
}

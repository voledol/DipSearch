package main;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "available-sites")
@Configuration
public class AvailableSites {
    private String xyz;
    private static List<Site> availableSites = new ArrayList<>();

    public String getXyz() {
        return xyz;
    }

    public void setXyz(String xyz) {
        this.xyz = xyz;
    }

    public  List<Site> getAvailableSites() {
        return availableSites;
    }

    public static void setAvailableSites(List<Site> availableSites) {
        AvailableSites.availableSites = availableSites;
    }
public static class Site {
        private String url;
        private String name;
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

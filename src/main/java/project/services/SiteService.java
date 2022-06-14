package project.services;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import project.Main;
import project.model.Site;
import project.repositoryes.PageRepository;
import project.repositoryes.SiteConnection;
import project.repositoryes.SiteRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {
    public final SiteRepository siteRepository;
    public final PageRepository pageRepository;

    public List<Site> getAllSites () {
        return siteRepository.findAll();
    }

    public long getSiteCount () {
        return siteRepository.count();
    }

    public void saveSitesIfNotExist () {
        List<Site> siteListFromDB = siteRepository.findAll();
        List<Site> siteList = Main.availableSites;
        if (siteListFromDB.size() == 0) {
            siteRepository.saveAll(siteList);
        } else {
            for (Site site : siteList) {
                for (Site st : siteListFromDB) {
                    if (!st.getUrl().equals(site.getUrl())) {
                        siteRepository.save(st);
                    } else {
                        continue;
                    }
                }

            }
        }


    }


}

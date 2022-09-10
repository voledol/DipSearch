package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.Main;
import project.model.Site;
import project.repositoryes.PageRepository;
import project.repositoryes.SiteRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<Site> getSiteById (Integer id) {
        return siteRepository.findById(id);
    }

    public void updateSiteIndexationStatus (String status, String site) {
        siteRepository.updateSiteStatus(status, site);
    }

    @Transactional
    public void updateSiteErrorAndStatusTime (String error, Date statusTime, Integer id) {
        siteRepository.updateSiteErrorAndStatus(error, statusTime, id);
    }

}

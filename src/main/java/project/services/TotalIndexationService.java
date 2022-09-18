package project.services;

import dto.ResultIndexing;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.Main;
import project.PageDuplicateCheck;
import project.model.Site;

import java.util.List;

@Service
@AllArgsConstructor
public class TotalIndexationService {
    public final IndexationService indexationService;
    public final PageCreatorService pageCreatorService;
    public final MapperService mapperService;
    public final SiteService siteService;
    public final IndexService indexService;
    public final PageService pageService;
    public final LemmaService lemmaService;
    public final Logger totalIndexationLogger = LogManager.getLogger(TotalIndexationService.class);

    public ResultIndexing startTotalIndexing() {
        if (Main.isIndexationRunning) {
            totalIndexationLogger.log(Level.INFO, "ИНДЕКСАЦИЯ УЖЕ ЗАПУЩЕНА");
            ResultIndexing resultIndexing = new ResultIndexing();
            resultIndexing.setResult("false");
            return resultIndexing;
        }
        totalIndexationLogger.log(Level.INFO, "ЗАПУЩЕНА ПОЛНАЯ ИНДЕКСАЦИЯ САЙТОВ");
        List<Site> sites = siteService.getAllSites();
        Main.isIndexationRunning = true;
        for (Site st : sites) {
            if (st.getStatus().equals("INDEXED")) {
                indexService.deleteAllIndexWithSiteId(st.getId());
                pageService.deleteAllPagesWithSiteId(st.getId());
                lemmaService.deleteAllLemmaWithSiteId(st.getId());
            }
            if (Main.isIndexationRunning) {
                siteService.updateSiteIndexationStatus("FAILED", st.getUrl());
                mapperService.getNodeLinkSet(st.getUrl());
                PageDuplicateCheck.existPages.clear();

            } else {
                continue;
            }
            siteService.updateSiteIndexationStatus("INDEXED", st.getUrl());
        }
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        return resultIndexing;
    }

    public ResultIndexing stopTotalIndexing() {
        Main.isIndexationRunning = false;
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        totalIndexationLogger.log(Level.INFO, "НАЧАТА ОСТАНОВКА ИНДЕКСАЦИИ САЙТОВ");
        return resultIndexing;
    }
}

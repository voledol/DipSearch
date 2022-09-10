package project.services;

import dto.ResultIndexing;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.Main;
import project.Nodelink;
import project.PageDuplicateCheck;
import project.model.Site;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TotalIndexationService {
    public final IndexationService indexationService;
    public final PageCreatorService pageCreatorService;
    public final MapperService mapperService;
    public final SiteService siteService;
    public final Logger totalIndexationLogger = LogManager.getLogger(TotalIndexationService.class);

    public ResultIndexing startTotalIndexing () {
        if(Main.isIndexationRunning){
            totalIndexationLogger.log(Level.INFO, "ИНДЕКСАЦИЯ УЖЕ ЗАПУЩЕНА");
            ResultIndexing resultIndexing = new ResultIndexing();
            resultIndexing.setResult("false");
            return resultIndexing;
        }
        totalIndexationLogger.log(Level.INFO, "ЗАПУЩЕНА ПОЛНАЯ ИНДЕКСАЦИЯ САЙТОВ");
        List<Site> sites = siteService.getAllSites();
        Main.isIndexationRunning = true;
        for(Site st: sites){
            siteService.updateSiteIndexationStatus("FAILED", st.getUrl());
        }
        for (Site st : sites) {
            if(Main.isIndexationRunning){
                mapperService.getNodeLinkSet(st.getUrl());
                PageDuplicateCheck.existPages.clear();
                siteService.updateSiteIndexationStatus("INDEXED", st.getUrl());
            }
        }
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        return resultIndexing;
    }
    public ResultIndexing stopTotalIndexing(){
        Main.isIndexationRunning = false;
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        totalIndexationLogger.log(Level.INFO, "НАЧАТА ОСТАНОВКА ИНДЕКСАЦИИ САЙТОВ");
        return resultIndexing;
    }
}

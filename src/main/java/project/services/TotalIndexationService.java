package project.services;

import dto.ResultIndexing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.Main;
import project.Nodelink;
import project.PageDuplicateCheck;
import project.model.Site;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TotalIndexationService {
    public final IndexationService indexationService;
    public final PageCreatorService pageCreatorService;
    public final MapperService mapperService;
    public final SiteService siteService;

    public ResultIndexing startTotalIndexing () {
        List<Site> sites = siteService.getAllSites();
        Main.isIndexationRunning = true;
        for(Site st: sites){
            siteService.updateSiteIndexationStatus("FAILED", st.getUrl());
        }
        for (Site st : sites) {
          Set<Nodelink> resultSet = mapperService.getNodeLinkSet(st.getUrl());
            PageDuplicateCheck.existPages.clear();
            resultSet.clear();
            siteService.updateSiteIndexationStatus("INDEXED", st.getUrl());
        }
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        return resultIndexing;
    }
    public ResultIndexing stopTotalIndexing(){
        Main.isIndexationRunning = false;
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        return resultIndexing;
    }
}

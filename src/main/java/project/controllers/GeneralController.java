package project.controllers;

import dto.ResultIndexing;
import dto.ResultSearchDto;
import dto.ResultStatisticDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.services.DBStatisticService;
import project.services.IndexationService;
import project.services.SearchSystemService;
import project.services.SiteService;

@Controller
@RequiredArgsConstructor
public class GeneralController {
    private final DBStatisticService dbStatisticService;
    private final SiteService siteService;
    public final IndexationService indexationService;
    public final SearchSystemService searchSystemService;

    @GetMapping ("/statistics")
    @ResponseBody
    public ResponseEntity<ResultStatisticDto> statistic () {
        siteService.saveSitesIfNotExist();
        return dbStatisticService.getStatistic();
    }

    @PostMapping ("/indexPage")
    @ResponseBody
    public ResponseEntity<ResultStatisticDto> indexingPage (String url) {
        return indexationService.indexPageRequest(url);
    }
    @GetMapping ("/search")
    @ResponseBody
    public ResponseEntity<ResultSearchDto> find (@RequestParam("query") String request, @RequestParam(name = "site", required = false)String site, @RequestParam("offset") String offset, @RequestParam("limit") String limit){
        return searchSystemService.find(request, site, offset, limit );

    }
    @GetMapping ("startIndexing")
    @ResponseBody
    public ResponseEntity<ResultIndexing> startIndexing(){
        return ResponseEntity.ok(indexationService.startTotalIndexing());
    }
}
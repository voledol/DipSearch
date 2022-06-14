package project.controllers;

import dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ResultDto> statistic () {
        siteService.saveSitesIfNotExist();
        return dbStatisticService.getStatistic();
    }
    @PostMapping ("/indexPage")
    @ResponseBody

    public ResponseEntity<ResultDto> indexingPage (String url) {
        return indexationService.indexPageRequest(url);
    }
    @GetMapping ("/search")
    @ResponseBody
    public JSONObject find (String request){
        return searchSystemService.find(request, "1", "1", "2" );

    }
}
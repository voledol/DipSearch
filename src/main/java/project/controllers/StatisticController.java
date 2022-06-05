package project.controllers;

import dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.springframework.beans.factory.parsing.PropertyEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import project.services.DBStatisticService;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final DBStatisticService dbStatisticService;

    @GetMapping ("/statistics")
    public ResponseEntity<ResultDto> statistic () {
        return dbStatisticService.getStatistic();
    }
}
package project.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.model.Site;
import project.model.SiteStatus;
import project.services.DBStatisticService;

import java.sql.Date;

@Controller
@RequiredArgsConstructor
public class StatisticController {
    private final DBStatisticService dbStatisticService;

    @GetMapping ("/statistics")
    @ResponseBody
    public ResponseEntity<ResultDto> statistic () {

            return dbStatisticService.getStatistic();
    }
}
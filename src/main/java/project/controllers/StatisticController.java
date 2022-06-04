package project.controllers;

import dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.services.DBStatisticService;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final DBStatisticService dbStatisticService;

    @GetMapping ("/statistics")
    public String statistic () {
        JSONObject ans = new JSONObject();
        ans.put("result", true);
        ans.put("error", "нет");
//        return dbStatisticService.getStatistic();
        return ans.toString();
    }
}

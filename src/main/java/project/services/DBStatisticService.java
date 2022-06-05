package project.services;

import dto.DetailedStatisticDto;
import dto.ResultDto;
import dto.StatisticDto;
import dto.TotalStatisticDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.model.Site;
import project.model.SiteStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс сборщик статистики содержащихс в БД данных о сайтах и процессе работы
 *
 * @author VG
 * @version 0.1
 **/
@Service
@RequiredArgsConstructor
public class DBStatisticService {
    private final PageServise pageServise;
    private final IndexServise indexServise;
    private final LemmaServise lemmaServise;
    private final SiteServise siteServise;

    public ResponseEntity<ResultDto> getStatistic () {
        try {
            ResultDto dto = new ResultDto();
            dto.setResult(true);
            dto.setStatisticDto(getStatisticDto());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.ok(ResultDto.getFalseResult("Нет ответа от БД"));
        }
    }

    private StatisticDto getStatisticDto () {
        return new StatisticDto(getTotalStatistic(), getDetailedStatistic());
    }

    private TotalStatisticDto getTotalStatistic () {
        return new TotalStatisticDto(1, 0, 0, true);
    }
//TODO
// написать стрим, не через цикл

    private List<DetailedStatisticDto> getDetailedStatistic () {
//        List<Site> siteList = siteServise.siteRepository.findAll();
        List<DetailedStatisticDto> result = new ArrayList<>();
        Site testSite = new Site();
        testSite.setId(1);
        testSite.setStatus(SiteStatus.INDEXED);
        testSite.setLastError("none");
        testSite.setName("test");
        testSite.setUrl("http://generalpage.com");
        testSite.setStatusTime(new Date(
                102165
        ));
        Integer pageCount = 0;
        Integer lemmaCount = 0;
        result.add(DetailedStatisticDto.getDto(testSite, pageCount, lemmaCount));
//        for (Site st : siteList) {
//            Integer pageCount = 0;
//            Integer lemmaCount = 0;
//            result.add(DetailedStatisticDto.getDto(st, pageCount, lemmaCount));
//        }
        return result;
    }

}


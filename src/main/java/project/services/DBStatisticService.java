package project.services;

import dto.DetailedStatisticDto;
import dto.ResultDto;
import dto.StatisticDto;
import dto.TotalStatisticDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Site;

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

    public ResultDto getStatistic () {
        try {
            ResultDto dto = new ResultDto();
            dto.setStatisticDto(getStatisticDto());
            return dto;
        } catch (Exception e) {
            return ResultDto.getFalseResult("Нет ответа от БД");
        }
    }

    private StatisticDto getStatisticDto () {
        return new StatisticDto(getTotalStatistic(), getDetailedStatistic());
    }

    private TotalStatisticDto getTotalStatistic () {
        return new TotalStatisticDto(0, 0, 0, true);
    }
//TODO
// написать стрим, не через цикл

    private List<DetailedStatisticDto> getDetailedStatistic () {
        List<Site> siteList = siteServise.siteRepository.findAll();
        List<DetailedStatisticDto> result = new ArrayList<>();
        for (Site st : siteList) {
            Integer pageCount = 0;
            Integer lemmaCount = 0;
            result.add(DetailedStatisticDto.getDto(st, pageCount, lemmaCount));
        }
        return result;
    }

}


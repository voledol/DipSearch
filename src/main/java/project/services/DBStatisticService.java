package project.services;

import dto.DetailedStatisticDto;
import dto.ResultStatisticDto;
import dto.StatisticDto;
import dto.TotalStatisticDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.Main;
import project.model.Site;

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
    private final PageService pageService;
    private final LemmaServise lemmaServise;
    private final SiteService siteService;

    public ResponseEntity<ResultStatisticDto> getStatistic () {
        try {
            ResultStatisticDto dto = new ResultStatisticDto();
            dto.setResult("true");
            dto.setStatisticDto(getStatisticDto());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.ok(ResultStatisticDto.getFalseStatisticResult("Нет ответа от БД"));
        }
    }

    private StatisticDto getStatisticDto () {
        return new StatisticDto(getTotalStatistic(), getDetailedStatistic());
    }

    private TotalStatisticDto getTotalStatistic () {
        Long pageCount = pageService.getPageCount();
        Long lemmaCount = lemmaServise.getLemmaCount();
        Long siteCount = siteService.getSiteCount();
        boolean indexing = Main.isIndexationRunning;
        return new TotalStatisticDto(pageCount,lemmaCount,siteCount,indexing);
    }
    private List<DetailedStatisticDto> getDetailedStatistic () {
        List<Site> siteList = siteService.getAllSites();
        List<DetailedStatisticDto> result = new ArrayList<>();
        siteList.stream().forEach(site -> {Integer pageCount = pageService.getPageCountBySiteId(site.getId());
            Integer lemmaCount = lemmaServise.getLemmaCountBySiteID(site.getId());
            if(site.getStatusTime()==null){
                site.setStatusTime(new Date(0));
            }
            result.add(DetailedStatisticDto.getDto(site, pageCount, lemmaCount));} );
        return result;
    }

}


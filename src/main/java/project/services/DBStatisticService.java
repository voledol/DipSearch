package project.services;

import dto.DetailedStatisticDto;
import dto.ResultStatisticDto;
import dto.StatisticDto;
import dto.TotalStatisticDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    private final PageService pageServise;
    private final IndexService indexServise;
    private final LemmaServise lemmaServise;
    private final SiteService siteServise;

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
        Long pageCount = pageServise.getPageCount();
        Long lemmaCount = lemmaServise.getLemmaCount();
        Long siteCount = siteServise.getSiteCount();
        boolean indexing = false;
        return new TotalStatisticDto(pageCount,lemmaCount,siteCount,indexing);
    }
    private List<DetailedStatisticDto> getDetailedStatistic () {
        List<Site> siteList = siteServise.getAllSites();
        List<DetailedStatisticDto> result = new ArrayList<>();
        siteList.stream().forEach(site -> {Integer pageCount = pageServise.getPageCountBySiteId(site.getId());
            Integer lemmaCount = lemmaServise.getLemmaCountBySiteID(site.getId());
            if(site.getLastError()==null){
                site.setLastError("noErrors");
            }if(site.getStatusTime()==null){
                site.setStatusTime(new Date(0));
            }
            result.add(DetailedStatisticDto.getDto(site, pageCount, lemmaCount));} );
        return result;
    }

}


package project.services;

import dto.DetailedStatisticDto;
import dto.ResultDto;
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
    private final PageServise pageServise;
    private final IndexService indexServise;
    private final LemmaServise lemmaServise;
    private final SiteService siteServise;

    public ResponseEntity<ResultDto> getStatistic () {
        try {
            ResultDto dto = new ResultDto();
            dto.setResult("true");
            dto.setStatisticDto(getStatisticDto());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.ok(ResultDto.getFalseStatisticResult("Нет ответа от БД"));
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
//TODO
// написать стрим, не через цикл

    private List<DetailedStatisticDto> getDetailedStatistic () {
        List<Site> siteList = siteServise.getAllSites();
        List<DetailedStatisticDto> result = new ArrayList<>();
//        Site testSite = new Site();
//        testSite.setId(1);
//        testSite.setStatus(SiteStatus.FAILED);
//        testSite.setLastError("testERROR");
//        testSite.setName("test");
//        testSite.setUrl("http://generalpage.com");
//        testSite.setStatusTime(new Date(
//                102165
//        ));
//        Integer pageCount = 100;
//        Integer lemmaCount = 100;
//        result.add(DetailedStatisticDto.getDto(testSite, pageCount, lemmaCount));
        for (Site st : siteList) {
            Integer pageCount = pageServise.getPageCountBySiteId(st.getId());
            Integer lemmaCount = lemmaServise.getLemmaCountBySiteID(st.getId());
            if(st.getLastError()==null){
                st.setLastError("noErrors");
            }if(st.getStatusTime()==null){
                st.setStatusTime(new Date(0));
            }
            result.add(DetailedStatisticDto.getDto(st, pageCount, lemmaCount));
            System.out.println("breakpoint");
        }
        return result;
    }

}


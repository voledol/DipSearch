package dto;

import lombok.Getter;
import lombok.Setter;
import project.model.Site;

import java.io.Serializable;
@Getter
@Setter
public class DetailedStatisticDto extends SiteDto implements Serializable {
    private Integer pageCount;
    private Integer lemmaCount;

    public static DetailedStatisticDto getDto (Site siteFromDb, Integer pageCount, Integer lemmaCount){
        DetailedStatisticDto dto = new DetailedStatisticDto();
        dto.setName(siteFromDb.getName());
        dto.setStatus(siteFromDb.getStatus().name());
        dto.setError(siteFromDb.getLastError());
        dto.setUrl(siteFromDb.getUrl());
        dto.setStatusTime(siteFromDb.getStatusTime().getTime());
        dto.setPageCount(pageCount);
        dto.setLemmaCount(lemmaCount);
        return dto;
    }
}

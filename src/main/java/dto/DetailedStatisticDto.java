package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import project.model.Site;

import java.io.Serializable;
@Getter
@Setter
public class DetailedStatisticDto extends SiteDto implements Serializable {

    @JsonProperty ("pages")
    private Integer pages;
    @JsonProperty("lemmas")
    private Integer lemmas;

    public static DetailedStatisticDto getDto (Site siteFromDb, Integer pageCount, Integer lemmaCount){
        DetailedStatisticDto dto = new DetailedStatisticDto();
        dto.setName(siteFromDb.getName());
        dto.setStatus(siteFromDb.getStatus().toString());
        dto.setError(siteFromDb.getLastError());
        dto.setUrl(siteFromDb.getUrl());
        dto.setStatusTime(siteFromDb.getStatusTime().getTime());
        dto.setPages(pageCount);
        dto.setLemmas(lemmaCount);
        return dto;
    }
}

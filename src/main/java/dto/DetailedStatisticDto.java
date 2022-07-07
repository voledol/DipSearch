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
    private String pages;
    @JsonProperty("lemmas")
    private String lemmas;

    public static DetailedStatisticDto getDto (Site siteFromDb, Integer pageCount, Integer lemmaCount){
        DetailedStatisticDto dto = new DetailedStatisticDto();
        dto.setName(siteFromDb.getName());
        dto.setStatus(siteFromDb.getStatus());
        dto.setError(siteFromDb.getLastError());
        dto.setUrl(siteFromDb.getUrl());
        dto.setStatusTime(siteFromDb.getStatusTime().getTime());
        dto.setPages(pageCount.toString().trim());
        dto.setLemmas(lemmaCount.toString().trim());
        return dto;
    }
}

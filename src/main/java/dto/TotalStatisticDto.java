package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TotalStatisticDto implements Serializable {
    @JsonProperty("pages")
    private Long pagesCount;
    @JsonProperty("lemmas")
    private Long lemmaCount;
    @JsonProperty("sites")
    private Long sites;
    @JsonProperty("isIndexing")
    private Boolean indexing;
}

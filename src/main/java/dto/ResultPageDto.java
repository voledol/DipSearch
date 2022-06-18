package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPageDto {
    private String site;
    private String siteName;
    @JsonProperty("url")
    private String url;
    private String title;
    private String snippet;
    private Double relevance;
}

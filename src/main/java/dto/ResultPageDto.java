package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResultPageDto implements Comparable<ResultPageDto> {
    private String site;
    private String siteName;
    @JsonProperty ("url")
    private String url;
    private String title;
    private String snippet;
    private Double relevance;

    @Override
    public int compareTo (ResultPageDto o) {
        return (-1)*(this.getRelevance().compareTo(o.getRelevance()));
    }
}


package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude (JsonInclude.Include.NON_EMPTY)
public class ResultSearchDto {
    @JsonProperty ("result")
    private String result;
    private String count;
    private String error;
    private List<ResultPageDto> data;
}

package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
//@JsonInclude (JsonInclude.Include.NON_EMPTY)
public class ResultDto implements Serializable {
    private Boolean result;
//    private String error;
    @JsonProperty ("statistics")
    private StatisticDto statisticDto;

    public static ResultDto getFalseResult (String error) {
        ResultDto dto = new ResultDto();
        dto.setResult(false);
        dto.setStatisticDto(null);
        return dto;
    }
}

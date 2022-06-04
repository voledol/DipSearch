package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StatisticDto implements Serializable {

    private TotalStatisticDto total;
    @JsonProperty ("detailed")
    private List<DetailedStatisticDto> detailedList;
}

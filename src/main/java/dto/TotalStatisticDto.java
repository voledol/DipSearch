package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TotalStatisticDto implements Serializable {
    private Integer pagesCount;
    private Integer lemmaCount;
    private Integer indexCount;
    private Boolean indexing;
}

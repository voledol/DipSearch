package dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SiteDto implements Serializable {
    private String url;
    private String name;
    private String status;
    private Long statusTime;
    private String error;
}

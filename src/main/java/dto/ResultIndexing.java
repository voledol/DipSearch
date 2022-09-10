package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultIndexing {
    private String result;
    private String error;

    public static  ResultIndexing pageIndexationTrue(){
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("true");
        resultIndexing.setError("no errors");
        return resultIndexing;
    }

    public static ResultIndexing pageIndexationFalse (){
        ResultIndexing resultIndexing = new ResultIndexing();
        resultIndexing.setResult("false");
        resultIndexing.setError("Данная страница находится за пределами сайтов, указанных в конфигурационном файле");
        return  resultIndexing;
    }
}

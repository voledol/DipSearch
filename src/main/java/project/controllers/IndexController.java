package project.controllers;

import project.services.PageServise;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    public final PageServise indexservice;

    public IndexController (PageServise indexservice) {
        this.indexservice = indexservice;
    }
    @RequestMapping
    @PostMapping("/indexpage")
    public JSONObject getPage(String path, Integer site_id){
        System.out.println( "Страница найдена " + indexservice.getPage(path, site_id));
       ;
        return new JSONObject();
    }
}

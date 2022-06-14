//package project.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RestController;
//import project.services.IndexationService;
//import project.services.PageServise;
//import org.json.JSONObject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//public class IndexController {
//    public final PageServise pageServise;
//    public final IndexationService indexationService;
//
//    @RequestMapping
//    @PostMapping("/indexPage")
//    public JSONObject indexPage(String path){
//        indexationService.indexPage(path);
//        return new JSONObject();
//    }
//}

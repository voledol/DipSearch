package project.services;

import project.repositoryes.PageRepository;
import lombok.RequiredArgsConstructor;
import project.model.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageServise {
    public final PageRepository pageJPAinterface;

    public Page getPage(String path, Integer site_id){
        return pageJPAinterface.findByPathAndSiteId(path, site_id);
    }
    public Page savePage(Page page){
       return pageJPAinterface.save(page);
    }
    public Optional<Page> getPageById(Integer id){
        return pageJPAinterface.findById(id);
    }
}

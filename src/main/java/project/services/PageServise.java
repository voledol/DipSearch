package project.services;

import project.repositoryes.PageRepository;
import lombok.RequiredArgsConstructor;
import project.model.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageServise {
    public final PageRepository pageJPAinterface;

    public Page getPage(String path, Integer site_id){
        return pageJPAinterface.findAllByPathAndSiteId(path, site_id);
    }
    public Page savePage(Page page){
       return pageJPAinterface.save(page);
    }
    public Optional<Page> getPageById(Integer id){
        return pageJPAinterface.findById(id);
    }
    public long getPageCount(){
        return pageJPAinterface.count();
    }
    public List<Page> getPageBySiteId (Integer site_id){
        return pageJPAinterface.findAllBySiteId(site_id);
    }
    public Integer getPageCountBySiteId(Integer site_id){
        List<Page> pages = getPageBySiteId(site_id);
        Integer pageCount;
        if(pages==null) {
            pageCount = 0;
        }
        else {
            pageCount = pages.size();
        }
        return pageCount;
    }
}

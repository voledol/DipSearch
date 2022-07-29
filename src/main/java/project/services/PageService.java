package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Page;
import project.repositoryes.PageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageService {
    public final PageRepository pageJPAInterface;

    public Page getPage (String path, Integer site_id) {
        return pageJPAInterface.findByPathAndSiteId(path, site_id).get(0);
    }

    public Page savePage (Page page) {
        return pageJPAInterface.save(page);
    }

    public Optional<Page> getPageById (Integer id) {
        return pageJPAInterface.findById(id);
    }

    public long getPageCount () {
        return pageJPAInterface.count();
    }

    public List<Page> getPageBySiteId (Integer site_id) {
        return pageJPAInterface.findAllBySiteId(site_id);
    }

    public Integer getPageCountBySiteId (Integer site_id) {
        List<Page> pages = getPageBySiteId(site_id);
        Integer pageCount;
        if (pages == null) {
            pageCount = 0;
        } else {
            pageCount = pages.size();
        }
        return pageCount;
    }
}

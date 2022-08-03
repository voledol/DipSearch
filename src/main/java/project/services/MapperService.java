package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.Nodelink;
import project.Mapper;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class MapperService {
    public final PageCreatorService pageCreatorService;
    public final IndexationService indexationService;
    public final SiteService siteService;

    public Set<Nodelink> getNodeLinkSet (String url) {
        Mapper mapper = new Mapper(new Nodelink(url), pageCreatorService, indexationService);
        siteService.updateSiteIndexationStatus("INDEXING", url);
        return new ForkJoinPool()
                .invoke(mapper);
    }

}

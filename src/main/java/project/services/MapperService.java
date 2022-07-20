package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class MapperService {
    public final PageCreatorService pageCreatorService;
    public final IndexationService indexationService;
    public final SiteService siteService;

    public Set<Nodelink> getNodeLinkSet (String url) {
        Mapper mapper = new Mapper(new Nodelink(url), pageCreatorService, indexationService, url);
        siteService.updateSiteIndexationStatus("INDEXING", url);
        return new ForkJoinPool(6, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false)
                .invoke(mapper);
    }

}

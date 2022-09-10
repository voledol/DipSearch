package project.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.Mapper;
import project.Nodelink;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class MapperService {
    public final PageCreatorService pageCreatorService;
    public final IndexationService indexationService;
    public final SiteService siteService;
    public final Logger mapServiceLogger = LogManager.getLogger(MapperService.class);

    public Set<Nodelink> getNodeLinkSet (String url) {
        Mapper mapper = new Mapper(new Nodelink(url), pageCreatorService, indexationService);
        siteService.updateSiteIndexationStatus("INDEXING", url);
        mapServiceLogger.log(Level.INFO, "Запущена индексация сайта: " + url);
        Set<Nodelink> nodelinkResultSet = new ForkJoinPool()
                .invoke(mapper);
        mapServiceLogger.log(Level.INFO, "Проиндексирован сайт: " + url);
        return nodelinkResultSet;
    }

}

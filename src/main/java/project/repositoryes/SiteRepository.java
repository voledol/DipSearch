package project.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Site;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {
    List<Site> findAll ();
    Site save(Site s);

}


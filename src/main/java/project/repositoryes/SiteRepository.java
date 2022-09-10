package project.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.model.Site;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {
    List<Site> findAll ();

    Site save (Site s);

    Optional<Site> findById (Integer id);

    @Transactional
    @Modifying
    @Query ("update Site set status =:status where url = :site")
    void updateSiteStatus (@Param ("status") String status, @Param ("site") String site);

    @Query ("update Site set status_time = :statusTime, last_error = :error  where id = :siteId ")
    void updateSiteErrorAndStatus (@Param ("statusTime") Date statusTime,
                                   @Param ("error") String error,
                                   @Param ("siteId") Integer id);

}


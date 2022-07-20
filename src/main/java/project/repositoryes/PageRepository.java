package project.repositoryes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    @Query ("FROM Page Where path = :path and site_id = :site_id")
    Page findByPathAndSiteId (@Param("path") String path, @Param("site_id") Integer site_id);
    Page save(Page page);
    Optional<Page> findById(Integer id);

    List<Page> findAllBySiteId(Integer site_id);
}

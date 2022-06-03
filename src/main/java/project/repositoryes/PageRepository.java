package project.repositoryes;

import project.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    Page findByPathAndSiteId (String path, Integer site_id);
    Page save(Page page);
    Optional<Page> findById(Integer id);
}

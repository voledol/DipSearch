package project.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.Index;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    Index save(Index index);

    @Query(value = "FROM Index Where page_id = :page_id", nativeQuery = true)
    List<Index> findAllByPageid(@Param("page_id") Integer page_id);

    @Query(value = "FROM Index Where page_id = 1 and lemma_id = 1", nativeQuery = true)
    List<Index> findAllByPageidAndLemmaid(Integer page_id, Integer lemma_id);

    @Query(value = "FROM Index Where lemma_id = :lemma_id", nativeQuery = true)
    List<Index> findAllByLemmaid(@Param("lemma_id") Integer lemma_id);

    @Query(value = "FROM Index where lemma_id = :lemma_id and page_id = :page_id", nativeQuery = true)
    List<Index> removePage(@Param("page_id") int page_id, @Param("lemma_id") int lemma_id);

    @Query(value = "FROM Index where lemma_id = :lemma_id and site_id = :site_id", nativeQuery = true)
    List<Index> findAllByLemmaidAndSiteId(Integer lemma_id, Integer site_id);
    @Transactional
    void deleteAllById(@Param("siteId") Integer siteId);
}

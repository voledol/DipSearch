package project.repositoryes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.model.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    Index save(Index index);
    @Query("FROM Index Where page_id = :page_id")
    List<Index> findAllByPageid(@Param("page_id") Integer page_id);
    List<Index> findAllByPageidAndLemmaid (Integer page_id, Integer lemma_id);
    List<Index> findAllByLemmaid(Integer lemma_id);
    @Query("FROM Index where lemma_id = :lemma_id and page_id = :page_id")
    List<Index> removePage(@Param("page_id") int page_id,@Param("lemma_id") int lemma_id);
}

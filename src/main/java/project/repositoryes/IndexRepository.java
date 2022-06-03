package project.repositoryes;

import project.model.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {
    Index save(Index index);
    List<Index> findAllByPageid(Integer page_id);
    Index findByPageidAndLemmaid(Integer page_id, Integer lemma_id);
    List<Index> findAllByLemmaid(Integer lemma_id);
}

package project.repositoryes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.model.Lemma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
    List<Lemma> findAllBylemmaOrderByFrequency(String lemma);

    Lemma save(Lemma lemma);

    List<Lemma> findAllBySiteId (Integer site_id);

    @Transactional
    void deleteAllBySiteId (Integer id);
}

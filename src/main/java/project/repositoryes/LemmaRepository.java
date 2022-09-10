package project.repositoryes;

import project.model.Lemma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
    List<Lemma> findAllBylemmaOrderByFrequency(String lemma);
    Lemma save(Lemma lemma);
    List<Lemma> findAllBySiteid (Integer site_id);

}

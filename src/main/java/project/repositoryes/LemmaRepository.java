package project.repositoryes;

import project.model.Lemma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
    Lemma findBylemma(String lemma);
    Lemma save(Lemma lemma);
}

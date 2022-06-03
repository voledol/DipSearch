package project.services;

import project.repositoryes.LemmaRepository;
import lombok.RequiredArgsConstructor;
import project.model.Lemma;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LemmaServise {
    public final LemmaRepository lemmaRepository;
    public Lemma getLemmaByName(String lemma){
        return lemmaRepository.findBylemma(lemma);
    }
    public void updateLemma(Lemma lemma){
        lemmaRepository.save(lemma);
    }
    public void addLemma(Lemma lemma){
        lemmaRepository.save(lemma);
    }
    public List<Lemma> findLemmaList(HashMap<String, Integer> searchString){
        List<Lemma> lemmaList = new ArrayList<>();
        for(Map.Entry entry: searchString.entrySet() ){

            Lemma lem = getLemmaByName(entry.getKey().toString());
            lemmaList.add(lem);
        }
        return lemmaList;
    }
}

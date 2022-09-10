package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Lemma;
import project.repositoryes.LemmaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LemmaService {
    public final LemmaRepository lemmaRepository;


    public Lemma getLemmaByName (String lemma) {
        List<Lemma> lemmaList = lemmaRepository.findAllBylemmaOrderByFrequency(lemma);
        Lemma lemmaFound = new Lemma();
        if (lemmaList.size() > 0) {
            lemmaFound = lemmaList.get(0);
        } else {
            lemmaFound = null;
        }
        return lemmaFound;
    }

    public void updateLemma (Lemma lemma) {
        lemmaRepository.save(lemma);
    }

    public void addLemma (Lemma lemma) {
        lemmaRepository.save(lemma);
    }

    public List<Lemma> findLemmaList (HashMap<String, Integer> searchString) {
        List<Lemma> lemmaList = new ArrayList<>();
        for (Map.Entry entry : searchString.entrySet()) {

            Lemma lem = getLemmaByName(entry.getKey().toString());
            lemmaList.add(lem);
        }
        return lemmaList;
    }

    public long getLemmaCount () {
        return lemmaRepository.count();
    }

    public List<Lemma> getLemmaListBySiteId (Integer site_id) {
        return lemmaRepository.findAllBySiteid(site_id);
    }

    public Integer getLemmaCountBySiteID (Integer site_id) {
        Integer lemmaCount;
        List<Lemma> lemmaList = getLemmaListBySiteId(site_id);
        if (lemmaList == null) {
            lemmaCount = 0;

        } else {
            lemmaCount = lemmaList.size();
        }
        return lemmaCount;
    }
}

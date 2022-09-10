package project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Index;
import project.repositoryes.IndexRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {
    public final IndexRepository indexRepository;

    public void save (Index index) {
        indexRepository.save(index);
    }

    public Index findIndexByPage_idAndLemm_id (Integer page_id, Integer lemm_id) {
        List<Index> index = indexRepository.findAllByPageidAndLemmaid(page_id, lemm_id);
        if (index.size() > 0) {
            return index.get(0);
        } else {
            Index indexDefault = new Index();
            indexDefault.setRank(0);
            index.add(indexDefault);
        }
        return index.get(0);
    }

    public List<Index> findIndexListByLemmaId (Integer lemma_id) {
        return indexRepository.findAllByLemmaid(lemma_id);
    }

    public List<Index> findIndexListByLemmaIdAndSiteId (Integer lemma_id, Integer site_id) {
        return indexRepository.findAllByLemmaidAndSiteId(lemma_id, site_id);
    }

    public Index removePage (int page_id, int lemma_id) {
        List<Index> indices = indexRepository.removePage(page_id, lemma_id);
        if(indices.size()!=0){
            return  indices.get(0);
        }
        else{return null;}
    }
}

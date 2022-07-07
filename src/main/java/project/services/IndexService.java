package project.services;

import org.springframework.data.jpa.repository.Query;
import project.repositoryes.IndexRepository;
import lombok.RequiredArgsConstructor;
import project.model.Index;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {
    public final IndexRepository indexRepository;
    public void save(Index index){
        indexRepository.save(index);
    }
    public List<Index> findIndexListByPageId(Integer id){
        return indexRepository.findAllByPageid(id);
    }

    public Index findIndexByPage_idAndLemm_id(Integer page_id, Integer lemm_id){
        List<Index> index = indexRepository.findAllByPageidAndLemmaid(page_id, lemm_id);
        if(index.size()>0){
            return index.get(0);
        }
        else{
            Index  indexDefault = new Index();
            indexDefault.setRank(0);
            index.add(indexDefault);
        }
        return index.get(0);
    }
    public List<Index> findIndexListByLemmaId(Integer lemma_id){
        return indexRepository.findAllByLemmaid(lemma_id);
    }
    public long getIndexCount(){
        return indexRepository.count();
    }
    public List<Index> removePage (int page_id, int lemma_id){
        return indexRepository.removePage(page_id, lemma_id);
    }
}

package project.services;

import project.repositoryes.IndexRepository;
import lombok.RequiredArgsConstructor;
import project.model.Index;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexServise {
    public final IndexRepository indexRepository;
    public void save(Index index){
        indexRepository.save(index);
    }
    public List<Index> findIndexListByPageId(Integer id){
        List<Index> resultSet = new ArrayList<>();

        List<Index> indexList = indexRepository.findAllByPageid(id);
        return resultSet;
    }
    public Index findIndexByPage_idAndLemm_id(Integer page_id, Integer lemm_id){
        return indexRepository.findByPageidAndLemmaid(page_id, lemm_id);
    }
    public List<Index> findIndexListByLemmaId(Integer lemma_id){
        return indexRepository.findAllByLemmaid(lemma_id);
    }
}

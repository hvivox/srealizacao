package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.repository.PrioridadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrioridadeService {
    @Autowired
    PrioridadeRepository prioridadeRepository;


    
    public List<Prioridade> findAllByFolha(Integer idFolha) {
        return prioridadeRepository.findAllPrioridadeIntoFolha(idFolha);
    }


    
    @Transactional
    public Prioridade save(Prioridade prioridade) {
        return prioridadeRepository.save(prioridade);

    }

    
    @Transactional
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Prioridade> prioridadeList = prioridadeRepository.findAllPrioridadeIntoFolha( folhaId);
        if(!prioridadeList.isEmpty()){
            prioridadeRepository.deleteAll(prioridadeList);
        }

    }



}
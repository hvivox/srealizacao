package com.hvivox.srealizacao.service.impl;

import com.hvivox.srealizacao.model.Folha;
import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.repository.PrioridadeRepository;
import com.hvivox.srealizacao.service.PrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PrioridadeServiceImpl implements PrioridadeService {
    @Autowired
    PrioridadeRepository prioridadeRepository;


    @Override
    public List<Prioridade> findAllByFolha(Integer idFolha) {
        return prioridadeRepository.findAllPrioridadeIntoFolha(idFolha);
    }


    @Override
    @Transactional
    public Prioridade save(Prioridade prioridade) {
        return prioridadeRepository.save(prioridade);

    }

    @Override
    @Transactional
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Prioridade> prioridadeList = prioridadeRepository.findAllPrioridadeIntoFolha( folhaId);
        if(!prioridadeList.isEmpty()){
            prioridadeRepository.deleteAll(prioridadeList);
        }

    }



}
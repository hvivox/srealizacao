package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.*;
import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.repository.PrioridadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrioridadeService {
    private static final String MSG_PRIORIDADE_EM_USO = "As prioridades com as informações passadas não podem ser " +
            "removida, pois estão em uso";
    private static final String MSG_PRIORIDADE_NAO_ENCONTRADA = "Não existem cadastros de prioridade associadas com " +
            "folha de numero %d";
    @Autowired
    PrioridadeRepository prioridadeRepository;
    
    public List<Prioridade> findAllByFolha(Integer idFolha) {
        return prioridadeRepository.findAllPrioridadeIntoFolha(idFolha);
    }
    
    
    @Transactional
    public Prioridade save(Prioridade prioridade) {
    
        try {
            return prioridadeRepository.save(prioridade);
        } catch (PrioridadeNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
        
    }
    
    
    @Transactional
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Prioridade> prioridadeList = prioridadeRepository.findAllPrioridadeIntoFolha(folhaId);
        try {
            prioridadeRepository.deleteAll(prioridadeList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_PRIORIDADE_NAO_ENCONTRADA, folhaId)) {
            };
            
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PRIORIDADE_EM_USO));
        }
        
        /*if(!prioridadeList.isEmpty()){
            prioridadeRepository.deleteAll(prioridadeList);
        }*/
        
    }
    
    
}
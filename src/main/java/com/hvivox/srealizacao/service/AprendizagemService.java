package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.EntidadeEmUsoException;
import com.hvivox.srealizacao.exception.EntidadeNaoEncontradaException;
import com.hvivox.srealizacao.exception.FolhaNaoEncontradoException;
import com.hvivox.srealizacao.model.Aprendizagem;
import com.hvivox.srealizacao.repository.AprendizagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AprendizagemService {
    
    private static final String MSG_APRENDIZAGEM_EM_USO = "As aprendizagens com as informações passadas não podem ser" +
            " removida, pois estão em uso";
    
    private static final String MSG_APRENDIZAGEM_NAO_ENCONTRADA = "Não existem cadastros de aprendizagens associadas " +
            "com folha de numero %d";
    
    @Autowired
    AprendizagemRepository aprendizagemRepository;
    
    public List<Aprendizagem> findAllByFolha(Integer idFolha) {
        return aprendizagemRepository.findAllAprendizagemIntoFolha(idFolha);
    }
    
    @Transactional
    public Aprendizagem save(Aprendizagem aprendizagem) {
        return aprendizagemRepository.save(aprendizagem);
        
    }
    
    
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Aprendizagem> aprendizagemList = aprendizagemRepository.findAllAprendizagemIntoFolha(folhaId);
        
        
        try {
            aprendizagemRepository.deleteAll(aprendizagemList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_APRENDIZAGEM_NAO_ENCONTRADA, folhaId)) {
            };
            
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_APRENDIZAGEM_EM_USO));
        }
        
            /*if(!aprendizagemList.isEmpty()){
                aprendizagemRepository.deleteAll( aprendizagemList );
            }*/
    }
    
    
}
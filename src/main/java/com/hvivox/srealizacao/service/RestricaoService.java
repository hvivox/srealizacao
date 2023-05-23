package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.EntidadeEmUsoException;
import com.hvivox.srealizacao.exception.EntidadeNaoEncontradaException;
import com.hvivox.srealizacao.model.Restricao;
import com.hvivox.srealizacao.repository.FolhaRepository;
import com.hvivox.srealizacao.repository.RestricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestricaoService {
    @Autowired
    RestricaoRepository restricaoRepository;
    @Autowired
    private FolhaRepository folhaRepository;
    
    private static final String MSG_RESTRICAO_EM_USO
            = "As restrições com as informações passadas não podem ser removida, pois estão em uso";
    
    private static final String MSG_RESTRICAO_NAO_ENCONTRADA
            = "Não existem cadastros de restrições associadas com folha de numero %d";

    
    public List<Restricao> findAllByFolha(Integer idFolha) {
        return restricaoRepository.findAllRestricaoIntoFolha(idFolha);
    }


    
    public Restricao save(Restricao restricao) {
        return restricaoRepository.save(restricao);

    }


    
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Restricao> restricaoList = restricaoRepository.findAllRestricaoIntoFolha(folhaId);
    
        try {
            restricaoRepository.deleteAll(restricaoList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_RESTRICAO_NAO_ENCONTRADA)) {
            };
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_RESTRICAO_EM_USO, folhaId));
        }
        
       /* if( !restricaoList.isEmpty() ){
            restricaoRepository.deleteAll(restricaoList);
        }*/
    }
    
    
}
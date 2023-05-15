package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Restricao;
import com.hvivox.srealizacao.repository.FolhaRepository;
import com.hvivox.srealizacao.repository.RestricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestricaoService {
    @Autowired
    RestricaoRepository restricaoRepository;
    @Autowired
    private FolhaRepository folhaRepository;
    

    
    public List<Restricao> findAllByFolha(Integer idFolha) {
        return restricaoRepository.findAllRestricaoIntoFolha(idFolha);
    }


    
    public Restricao save(Restricao restricao) {
        return restricaoRepository.save(restricao);

    }


    
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Restricao> restricaoList = restricaoRepository.findAllRestricaoIntoFolha(folhaId);
        
        if( !restricaoList.isEmpty() ){
            restricaoRepository.deleteAll(restricaoList);
        }
        
    }
    
    
}
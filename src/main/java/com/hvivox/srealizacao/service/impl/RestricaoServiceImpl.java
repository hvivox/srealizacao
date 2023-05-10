package com.hvivox.srealizacao.service.impl;

import com.hvivox.srealizacao.model.Restricao;
import com.hvivox.srealizacao.repository.FolhaRepository;
import com.hvivox.srealizacao.repository.RestricaoRepository;
import com.hvivox.srealizacao.service.RestricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestricaoServiceImpl implements RestricaoService {
    @Autowired
    RestricaoRepository restricaoRepository;
    @Autowired
    private FolhaRepository folhaRepository;
    

    @Override
    public List<Restricao> findAllByFolha(Integer idFolha) {
        return restricaoRepository.findAllRestricaoIntoFolha(idFolha);
    }


    @Override
    public Restricao save(Restricao restricao) {
        return restricaoRepository.save(restricao);

    }


    @Override
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Restricao> restricaoList = restricaoRepository.findAllRestricaoIntoFolha(folhaId);
        
        if( !restricaoList.isEmpty() ){
            restricaoRepository.deleteAll(restricaoList);
        }
        
    }
    
    
}
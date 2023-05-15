package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Gratidao;
import com.hvivox.srealizacao.repository.GratidaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GratidaoService {
    @Autowired
    GratidaoRepository gratidaoRepository;


    
    public List<Gratidao> findAllByFolha(Integer idFolha) {
        return gratidaoRepository.findAllGratidaoIntoFolha(idFolha);
    }


    
    @Transactional
    public Gratidao save(Gratidao gratidao) {
        return gratidaoRepository.save(gratidao);

    }

    
    @Transactional
    public void deleteTodosDaFolha(Integer idFolha) {
        List<Gratidao> gratidaoList = gratidaoRepository.findAllGratidaoIntoFolha( idFolha );
        
        if(!gratidaoList.isEmpty()){
            gratidaoRepository.deleteAll( gratidaoList );
        }
        
    }
    
    
}
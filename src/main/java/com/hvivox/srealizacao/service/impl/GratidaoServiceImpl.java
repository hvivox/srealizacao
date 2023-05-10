package com.hvivox.srealizacao.service.impl;

import com.hvivox.srealizacao.model.Gratidao;
import com.hvivox.srealizacao.repository.GratidaoRepository;
import com.hvivox.srealizacao.service.GratidaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GratidaoServiceImpl implements GratidaoService {
    @Autowired
    GratidaoRepository gratidaoRepository;


    @Override
    public List<Gratidao> findAllByFolha(Integer idFolha) {
        return gratidaoRepository.findAllGratidaoIntoFolha(idFolha);
    }


    @Override
    @Transactional
    public Gratidao save(Gratidao gratidao) {
        return gratidaoRepository.save(gratidao);

    }

    @Override
    @Transactional
    public void deleteTodosDaFolha(Integer idFolha) {
        List<Gratidao> gratidaoList = gratidaoRepository.findAllGratidaoIntoFolha( idFolha );
        
        if(!gratidaoList.isEmpty()){
            gratidaoRepository.deleteAll( gratidaoList );
        }
        
    }
    
    
}
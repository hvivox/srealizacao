package com.hvivox.srealizacao.service.impl;

import com.hvivox.srealizacao.model.Aprendizagem;
import com.hvivox.srealizacao.model.Gratidao;
import com.hvivox.srealizacao.repository.AprendizagemRepository;
import com.hvivox.srealizacao.service.AprendizagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AprendizagemServiceImpl implements AprendizagemService {
    @Autowired
    AprendizagemRepository aprendizagemRepository;

 

    @Override
    public List<Aprendizagem> findAllByFolha(Integer idFolha) {
        return aprendizagemRepository.findAllAprendizagemIntoFolha(idFolha);
    }


    @Override
    @Transactional
    public Aprendizagem save(Aprendizagem aprendizagem) {
        return aprendizagemRepository.save(aprendizagem);

    }


    
    @Override
    public void deleteTodosDaFolha(Integer idFolha) {
        List<Aprendizagem> aprendizagemList = aprendizagemRepository.findAllAprendizagemIntoFolha( idFolha );
    
        if(!aprendizagemList.isEmpty()){
            aprendizagemRepository.deleteAll( aprendizagemList );
        }
    }
    
    
}
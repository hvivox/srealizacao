package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Aprendizagem;
import com.hvivox.srealizacao.repository.AprendizagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AprendizagemService {
    @Autowired
    AprendizagemRepository aprendizagemRepository;

 

    
    public List<Aprendizagem> findAllByFolha(Integer idFolha) {
        return aprendizagemRepository.findAllAprendizagemIntoFolha(idFolha);
    }


    
    @Transactional
    public Aprendizagem save(Aprendizagem aprendizagem) {
        return aprendizagemRepository.save(aprendizagem);

    }


    
    
    public void deleteTodosDaFolha(Integer idFolha) {
        List<Aprendizagem> aprendizagemList = aprendizagemRepository.findAllAprendizagemIntoFolha( idFolha );
    
        if(!aprendizagemList.isEmpty()){
            aprendizagemRepository.deleteAll( aprendizagemList );
        }
    }
    
    
}
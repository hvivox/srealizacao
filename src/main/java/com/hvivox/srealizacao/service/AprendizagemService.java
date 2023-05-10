package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Aprendizagem;

import java.util.List;

public interface AprendizagemService {
  
    List<Aprendizagem> findAllByFolha( Integer idFolha);
    Aprendizagem save(Aprendizagem prioridade);
    
    void deleteTodosDaFolha(Integer id);
}

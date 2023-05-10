package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Prioridade;

import java.util.List;

public interface PrioridadeService {


    List<Prioridade> findAllByFolha( Integer idFolha);
    Prioridade save(Prioridade prioridade);
    void deleteTodosDaFolha(Integer folhaId);


}

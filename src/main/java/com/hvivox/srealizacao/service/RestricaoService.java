package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Restricao;

import java.util.List;

public interface RestricaoService {
   
    List<Restricao> findAllByFolha( Integer idFolha);
    Restricao save(Restricao restricao);
   
    void deleteTodosDaFolha(Integer folhaId);

}

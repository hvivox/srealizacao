package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Gratidao;

import java.util.List;

public interface GratidaoService {
    

    List<Gratidao> findAllByFolha( Integer idFolha);
    Gratidao save(Gratidao prioridade);

    void deleteTodosDaFolha(Integer id);
}

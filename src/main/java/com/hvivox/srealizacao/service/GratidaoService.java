package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.EntidadeEmUsoException;
import com.hvivox.srealizacao.exception.EntidadeNaoEncontradaException;
import com.hvivox.srealizacao.model.Gratidao;
import com.hvivox.srealizacao.repository.GratidaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GratidaoService {
    @Autowired
    GratidaoRepository gratidaoRepository;
    
    private static final String MSG_GRATIDAO_EM_USO
            = "As gratidões com as informações passadas não podem ser removida, pois estão em uso";
    
    private static final String MSG_RESTRICAO_NAO_ENCONTRADA
            = "Não existem cadastros de gratidões associadas com folha de numero %d";
    
    public List<Gratidao> findAllByFolha(Integer idFolha) {
        return gratidaoRepository.findAllGratidaoIntoFolha(idFolha);
    }


    
    @Transactional
    public Gratidao save(Gratidao gratidao) {
        return gratidaoRepository.save(gratidao);

    }

    
    @Transactional
    public void deleteTodosDaFolha(Integer folhaId) {
        List<Gratidao> gratidaoList = gratidaoRepository.findAllGratidaoIntoFolha( folhaId );
    
        try {
            gratidaoRepository.deleteAll(gratidaoList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_RESTRICAO_NAO_ENCONTRADA, folhaId)) {
            };
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRATIDAO_EM_USO));
        }
        
        /*if(!gratidaoList.isEmpty()){
            gratidaoRepository.deleteAll( gratidaoList );
        }*/
        
        
        
    }
    
    
}
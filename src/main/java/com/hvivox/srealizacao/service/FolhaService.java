package com.hvivox.srealizacao.service;


import com.hvivox.srealizacao.exception.EntidadeNaoEncontradaException;
import com.hvivox.srealizacao.model.*;
import com.hvivox.srealizacao.repository.FolhaRepository;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;





@Log4j2
@Service
public class FolhaService {
    @Autowired
    FolhaRepository folhaRepository;
    
    @Autowired
    private PrioridadeService prioridadeService;
    
    @Autowired
    private RestricaoService restricaoService;
    
    @Autowired
    private GratidaoService gratidaoService;
    
    @Autowired
    private AprendizagemService aprendizagemService;
    
    
    public Page<Folha> findAll(Specification<Folha> spec, Pageable pageable) {
        return folhaRepository.findAll(spec, pageable);
    }
    
    
    public Folha findById(Integer idFolha) {
        return folhaRepository.findById(idFolha).orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
        
    }
    
    
    @Transactional
    public Folha save(Folha folha) {
    
        log.debug("POST Salvar dados {} ", folha.toString());
        List<Prioridade> prioridades = new ArrayList<>();
        List<Restricao> restricoes = new ArrayList<>();
        List<Gratidao> gratidoes = new ArrayList<>();
        List<Aprendizagem> aprendizagens = new ArrayList<>();
    
        for (Prioridade prioridadeEncontrada : folha.getPrioridadeList()) {
            Prioridade prioridade = new Prioridade();
            BeanUtils.copyProperties(prioridadeEncontrada, prioridade);
            prioridade.setFolha(folha);
            prioridades.add(prioridade);
        }
    
        for (Restricao restricaoEncontrada : folha.getRestricaoList()) {
            Restricao restricao = new Restricao();
            BeanUtils.copyProperties(restricaoEncontrada, restricao);
            restricao.setFolha(folha);
            restricoes.add(restricao);
        }
    
        for (Gratidao gratidaoEncontrada : folha.getGratidaoList()) {
            Gratidao gratidao = new Gratidao();
            BeanUtils.copyProperties(gratidaoEncontrada, gratidao);
            gratidao.setFolha(folha);
            gratidoes.add(gratidao);
        }
    
        for (Aprendizagem aprendizagemEncontrada : folha.getAprendizagemList()) {
            Aprendizagem aprendizagem = new Aprendizagem();
            BeanUtils.copyProperties(aprendizagemEncontrada, aprendizagem);
            aprendizagem.setFolha(folha);
            aprendizagens.add(aprendizagem);
        }
    
        folha.setPrioridadeList(prioridades);
        folha.setRestricaoList(restricoes);
        folha.setGratidaoList(gratidoes);
        folha.setAprendizagemList(aprendizagens);
        
        return folhaRepository.save(folha);
    }
    
    @Transactional
    public Folha update(Folha folhaEncontrada, Folha folhaInput) {
        //folhaEncontrada.setId(folhaEncontrada.getId());
        folhaEncontrada.setFoco(folhaInput.getFoco());
        folhaEncontrada.setDtarealizacao(folhaInput.getDtarealizacao());
        folhaEncontrada.setNotadia(folhaInput.getNotadia());
        folhaEncontrada.setObservacao(folhaInput.getObservacao());
        folhaEncontrada.setStatus(folhaInput.getStatus());
    
        Folha folhaObjectID = new Folha();
        folhaObjectID.setId(folhaEncontrada.getId());
    
        prioridadeService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getPrioridadeList().isEmpty()) {
            
            for (Prioridade prioridadeEncontrada : folhaInput.getPrioridadeList()) {
                Prioridade prioridade = new Prioridade();
                BeanUtils.copyProperties(prioridadeEncontrada, prioridade, "folha");
                //prioridade.setFolha(folhaObjectID);
                prioridade.setFolha(folhaObjectID);
                // log.debug("atributo prioridade {}", prioridade);
                folhaEncontrada.getPrioridadeList().add(prioridade);
            }
    
        }
    
        restricaoService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getRestricaoList().isEmpty()) {
    
            for (Restricao restricaoEncontrada : folhaInput.getRestricaoList()) {
                
                Restricao restricao = new Restricao();
                BeanUtils.copyProperties(restricaoEncontrada, restricao, "folha");
                restricao.setFolha(folhaObjectID);
                folhaEncontrada.getRestricaoList().add(restricao);
            }
    
        }
    
    
        gratidaoService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getGratidaoList().isEmpty()) {
            for (Gratidao gratidaoEncontrada : folhaInput.getGratidaoList()) {
                Gratidao gratidao = new Gratidao();
                BeanUtils.copyProperties(gratidaoEncontrada, gratidao, "folha");
                gratidao.setFolha(folhaObjectID);
                folhaEncontrada.getGratidaoList().add(gratidao);
            }
    
        }
    
    
        aprendizagemService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getAprendizagemList().isEmpty()) {
            
            for (Aprendizagem aprendizagemEncontrada : folhaInput.getAprendizagemList()) {
                Aprendizagem aprendizagem = new Aprendizagem();
                BeanUtils.copyProperties(aprendizagemEncontrada, aprendizagem, "folha");
                aprendizagem.setFolha(folhaObjectID);
                folhaEncontrada.getAprendizagemList().add(aprendizagem);
            }
    
        }
        
        return folhaRepository.save(folhaEncontrada);
        
    }
    

    
    @Transactional
    public void delete(Folha folha) {
        folhaRepository.delete(folha);
    }
    
    
}

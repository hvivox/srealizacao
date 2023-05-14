package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.model.*;
import com.hvivox.srealizacao.service.*;
import com.hvivox.srealizacao.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("folhas")
public class FolhaController {
    
    @Autowired
    private FolhaService folhaService;
    
    @Autowired
    private PrioridadeService prioridadeService;
    @Autowired
    private RestricaoService restricaoService;
    @Autowired
    private GratidaoService gratidaoService;
    
    @Autowired
    private AprendizagemService aprendizagemService;
    
    @GetMapping
    public ResponseEntity<Page<Folha>> getAll(SpecificationTemplate.FolhaSpec spec, @PageableDefault(page = 0, size =
            10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<Folha> folhaPage = folhaService.findAll(spec, pageable);
        
        return ResponseEntity.status(HttpStatus.OK).body(folhaPage);
    }
    
    @GetMapping("{idFolha}")
    public Folha getById(@PathVariable(value = "idFolha") Integer idFolha) {
        Folha folhaOptional = folhaService.findById(idFolha);
        return folhaOptional;
    }
    
    
    @PostMapping
    public ResponseEntity<Folha> save(@RequestBody Folha folha) {
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
        
        return ResponseEntity.status(HttpStatus.CREATED).body(folhaService.save(folha));
    }
    
    
    @PutMapping("/{idFolha}")
    @Transactional
    public ResponseEntity<Object> update(@PathVariable Integer idFolha, @RequestBody Folha folhaInput) {
        // log.debug("PUT Update dados {} e idFolha:{} ", folha.toString(), idFolha);
        Folha folhaEncontradaOptional = folhaService.findById(idFolha);
        
        
        if (folhaEncontradaOptional == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folha não encontrada!");
            
        } else {
            
            Folha folhaEncontrada = folhaEncontradaOptional;
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
            
            folhaEncontrada = folhaService.save(folhaEncontrada);
            return ResponseEntity.status(HttpStatus.OK).body(folhaEncontrada);
            
        }
    }
    
}

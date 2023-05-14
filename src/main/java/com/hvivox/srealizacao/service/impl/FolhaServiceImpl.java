package com.hvivox.srealizacao.service.impl;


import com.hvivox.srealizacao.exception.EntidadeNaoEncontradaException;
import com.hvivox.srealizacao.model.Folha;
import com.hvivox.srealizacao.repository.FolhaRepository;
import com.hvivox.srealizacao.service.FolhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FolhaServiceImpl implements FolhaService {
    @Autowired
    FolhaRepository folhaRepository;
    
    @Override
    public Page<Folha> findAll(Specification<Folha> spec, Pageable pageable) {
        return folhaRepository.findAll(spec, pageable);
    }
    
    @Override
    public Folha findById(Integer idFolha) {
        return folhaRepository.findById(idFolha).orElseThrow(()
                -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
        
    }
    
    @Override
    @Transactional
    public Folha save(Folha folha) {
        return folhaRepository.save(folha);
    }
    
    @Override
    @Transactional
    public void delete(Folha folha) {
        folhaRepository.delete(folha);
    }
    
    
}

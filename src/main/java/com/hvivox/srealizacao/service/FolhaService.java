package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.model.Folha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface FolhaService {
    Page<Folha> findAll(Specification<Folha> spec, Pageable pageable );
    Folha findById(Integer idFolha);

    Folha save(Folha folha);

    void delete(Folha folha);

}

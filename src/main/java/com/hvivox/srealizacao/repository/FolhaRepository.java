package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Folha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FolhaRepository extends JpaRepository<Folha, Integer>, JpaSpecificationExecutor<Folha> {

}

package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Integer>, JpaSpecificationExecutor<Sheet> {

}

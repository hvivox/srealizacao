package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrioridadeRepository extends JpaRepository<Prioridade, Integer> {
    @Query(value="select * from Prioridade where idFolha = :folhaId", nativeQuery = true)
    List<Prioridade> findAllPrioridadeIntoFolha(@Param("folhaId") Integer folhaID);

}

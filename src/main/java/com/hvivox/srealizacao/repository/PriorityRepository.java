package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Integer> {
    @Query(value="select * from Prioridade where idFolha = :folhaId", nativeQuery = true)
    List<Priority> findAllPriorityIntoSheet(@Param("folhaId") Integer folhaID);

}

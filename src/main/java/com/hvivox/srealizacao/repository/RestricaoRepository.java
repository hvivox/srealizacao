package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Restricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestricaoRepository extends JpaRepository<Restricao, Integer> {
    @Query(value="select * from Restricao where idFolha = :folhaId", nativeQuery = true)
    List<Restricao> findAllRestricaoIntoFolha(@Param("folhaId") Integer folhaID);

}

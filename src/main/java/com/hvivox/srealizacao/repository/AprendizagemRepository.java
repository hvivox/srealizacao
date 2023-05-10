package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Aprendizagem;
import com.hvivox.srealizacao.model.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprendizagemRepository extends JpaRepository<Aprendizagem, Integer> {
    @Query(value="select * from Aprendizagem where idfolha = :folhaId", nativeQuery = true)
    List<Aprendizagem> findAllAprendizagemIntoFolha(@Param("folhaId") Integer folhaID);

}

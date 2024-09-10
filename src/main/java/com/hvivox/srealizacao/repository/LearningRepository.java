package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Learning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRepository extends JpaRepository<Learning, Integer> {
    @Query(value="select * from Aprendizagem where idfolha = :folhaId", nativeQuery = true)
    List<Learning> findAllLearningIntoSheet(@Param("folhaId") Integer folhaID);

}

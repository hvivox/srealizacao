package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Gratitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GratitudeRepository extends JpaRepository<Gratitude, Integer> {
    @Query(value="select * from Gratidao where idFolha = :folhaId", nativeQuery = true)
    List<Gratitude> findAllGratitudeIntoSheet(@Param("folhaId") Integer folhaID);

}

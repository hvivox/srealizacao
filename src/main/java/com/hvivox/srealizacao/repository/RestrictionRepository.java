package com.hvivox.srealizacao.repository;

import com.hvivox.srealizacao.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictionRepository extends JpaRepository<Restriction, Integer> {
    @Query(value="select * from Restricao where idFolha = :folhaId", nativeQuery = true)
    List<Restriction> findAllRestrictionIntoSheet(@Param("folhaId") Integer folhaID);

}

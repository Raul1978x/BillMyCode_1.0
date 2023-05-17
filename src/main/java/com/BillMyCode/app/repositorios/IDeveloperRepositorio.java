package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeveloperRepositorio extends JpaRepository<Developer, Long> {

    @Query("SELECT d FROM Developer d WHERE d.seniority = :seniority")
    public List<Developer> searchBySeniority(@Param("seniority") String seniority);

}

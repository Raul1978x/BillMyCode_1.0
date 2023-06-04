package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeveloperRepository extends JpaRepository<Developer, Long> {

    /**
     * Query searchBySeniority: Busca developers segun un nivel de seniority
     *
     * @param seniority
     *
     * @return Lista de Developer
     */
    @Query("SELECT d FROM Developer d WHERE d.seniority = :seniority")
    List<Developer> searchBySeniority(@Param("seniority") String seniority);

    /**
     * Query seachByEmail: Busca un developer segun un email
     *
     * @param email
     *
     * @return Un Developer
     */
    @Query("SELECT d FROM Developer d WHERE d.email = :email")
    public Developer seachByEmail(@Param("email") String email);


}

package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountantRepository extends JpaRepository<Accountant, Long> {

    /**
     * Query seachByEmail: Busca un Contador segun un email
     *
     * @param email
     *
     * @return Un Contador (Accountant)
     */
    @Query("SELECT a FROM Accountant a WHERE a.email = :email")
    public Accountant seachByEmail(@Param("email") String email);
}

package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {
    /**
     * Query seachByEmail: Busca un Administrador segun un email
     *
     * @param email
     *
     * @return Un Admin (Accountant)
     */
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    public Admin searchByEmail(@Param("email") String email);
}

package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepositorio extends JpaRepository<Admin, Long> {
}

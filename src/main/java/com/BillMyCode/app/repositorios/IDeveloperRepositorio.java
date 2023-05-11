package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeveloperRepositorio extends JpaRepository<Developer, Long> {
}

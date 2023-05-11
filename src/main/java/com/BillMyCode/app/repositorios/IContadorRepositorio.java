package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Contador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContadorRepositorio extends JpaRepository<Contador, Long> {
}

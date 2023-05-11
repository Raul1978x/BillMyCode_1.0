package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpresaRepositorio extends JpaRepository<Empresa, Long> {
}

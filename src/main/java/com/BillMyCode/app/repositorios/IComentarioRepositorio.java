package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentarioRepositorio extends JpaRepository<Comentario, Long> {
}

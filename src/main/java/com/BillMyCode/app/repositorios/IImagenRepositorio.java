package com.BillMyCode.app.repositorios;

import com.BillMyCode.app.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenRepositorio extends JpaRepository<Imagen, Long> {
}

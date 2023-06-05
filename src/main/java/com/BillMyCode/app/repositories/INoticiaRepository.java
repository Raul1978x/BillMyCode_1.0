package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INoticiaRepository extends JpaRepository<Noticia, Long> {
}
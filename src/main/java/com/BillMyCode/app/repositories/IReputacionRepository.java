package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Reputacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReputacionRepository extends JpaRepository<Reputacion, Long> {
}

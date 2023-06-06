package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestRepository extends JpaRepository<Quest, Long> {
}

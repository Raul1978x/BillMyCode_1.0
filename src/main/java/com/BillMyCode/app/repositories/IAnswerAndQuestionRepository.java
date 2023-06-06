package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.AnswerAndQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerAndQuestionRepository extends JpaRepository<AnswerAndQuestion, Long> {

}

package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.AnswerAndQuestion;
import com.BillMyCode.app.repositories.IAnswerAndQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AnswerAndQuestionService {

    @Autowired
    private IAnswerAndQuestionRepository anqRepository;

    @Transactional
    public AnswerAndQuestion createQuest(String pregunta){
        AnswerAndQuestion anq = new AnswerAndQuestion();
        anq.setQuest(pregunta);
        anq.setFechaQuest(new Date());
        return anqRepository.save(anq);
    }

    @Transactional
    public AnswerAndQuestion createAnswer(String respuesta, Long id){
        AnswerAndQuestion anq = anqRepository.findById(id).get();
        anq.setAnswer(respuesta);
        anq.setFechaAnswer(new Date());
        anq.setStatus(true);
        return anqRepository.save(anq);
    }

}

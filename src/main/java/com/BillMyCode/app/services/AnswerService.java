package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Answer;
import com.BillMyCode.app.repositories.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private IAnswerRepository answerRepository;


    @Transactional(readOnly = true)
    public List<Answer> getAllAnswer(){
        return answerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Answer getAnswerById(Long id){
        return answerRepository.findById(id).get();
    }

/*    @Transactional
    public Answer createAnswer(String respuesta){
        Answer answer = new Answer();
        answer.setRespuesta(respuesta);
        answer.setFecha(new Date());
        answerRepository.save(answer);
        return answer;
    }

    @Transactional
    public Answer updateAnswer(Long id, String respuesta){
       Answer result = answerRepository.findById(id).get();
       if (result != null){
           result.setFecha(new Date());
           result.setRespuesta(respuesta);
           answerRepository.save(result);
           return result;
       }
       return null;
    }*/

    @Transactional
    public void deleteAnswerById(Long id){
        answerRepository.deleteById(id);
    }


}

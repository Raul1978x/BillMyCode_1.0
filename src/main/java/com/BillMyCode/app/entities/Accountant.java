package com.BillMyCode.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 */
@Getter
@Setter
@Entity
public class Accountant extends User {

    @OneToOne
    private Reputacion reputacion;
    private String matricula;
    private String especializacion;
    private Double honorarios;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Developer> developers;
    @OneToMany
    private List<AnswerAndQuestion> answerList;
}
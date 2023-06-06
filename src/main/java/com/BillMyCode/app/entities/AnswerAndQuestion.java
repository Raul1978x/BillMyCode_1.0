package com.BillMyCode.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class AnswerAndQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status=false;
    @Lob
    private String quest;
    private String answer;
    @Temporal(TemporalType.DATE)
    private Date fechaQuest;
    @Temporal(TemporalType.DATE)
    private Date fechaAnswer;
}

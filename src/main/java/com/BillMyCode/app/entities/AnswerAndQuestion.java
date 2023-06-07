package com.BillMyCode.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Lock;

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
    @Column(columnDefinition = "LONGTEXT")
    private String quest;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String answer;
    @Temporal(TemporalType.DATE)
    private Date fechaQuest;
    @Temporal(TemporalType.DATE)
    private Date fechaAnswer;
}

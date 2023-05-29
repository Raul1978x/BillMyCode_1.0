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
    private Boolean status;
    @OneToMany
    private List<Comment> comentario;
    @OneToMany
    private List<Developer> developers;
}
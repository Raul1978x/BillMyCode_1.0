package com.BillMyCode.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    private String matricula;
    private String especializacion;
    private Double honorarios;
    private Boolean status;
    private Double reputacion;
    @OneToMany
    private List<Comment> comentario;
    @OneToMany
    private List<Developer> developers;
}
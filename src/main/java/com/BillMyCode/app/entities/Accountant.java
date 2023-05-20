package com.BillMyCode.app.entities;

import jakarta.persistence.ElementCollection;
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

    private Double reputacion;
    @OneToMany
    private List<Comment> comments;
    private String matricula;
    @ElementCollection
    private List<String> especializaciones;
    private Double honorarios;
    private Boolean status;
    @OneToMany
    private List<Developer> developers;
}
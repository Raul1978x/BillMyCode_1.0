/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BillMyCode.app.entities;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author agust
 */
@Getter
@Setter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Lob
    private String descripcion;
    @OneToOne
    private Image logo;
    @OneToOne
    private Reputacion reputacion;
    @OneToMany
    private List<Comment> comment;
    private Double salarioPromedio;
    private Double salarioHistorico;
    private Boolean status;
    private String password;

    @OneToMany
    private List<Developer> developer;

    @OneToOne
    private Contact contacto;







}

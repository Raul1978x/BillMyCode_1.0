/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BillMyCode.app.entidades;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author agust
 */
@Getter
@Setter
@Entity
public class Developer extends Usuario{


    private Double salario;
    private String seniority;
    private String especialidad;
    @Lob
    private String descripcion;
    @OneToOne
    private Comentario comentario;
    @ManyToMany
    private List<Empresa> empresa;
    @OneToOne
    private Contador contador;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BillMyCode.app.entidades;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Lob
    private String descripcion;
    private String contacto;
    private String razonSocial;
    private String email;
    private String direccion;
    private String telefono;
    private String password;
    @OneToOne
    private Imagen logo;
    @OneToMany
    private List<Developer> developer;
    private Double reputacion;
    @OneToMany
    private List<Comentario> comentario;
    private Boolean status;

}

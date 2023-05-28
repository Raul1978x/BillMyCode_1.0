package com.BillMyCode.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Reputacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private List<Integer> calificaciones;
    private int cantidadCalificaciones;


}

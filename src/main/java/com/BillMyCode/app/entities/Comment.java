/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BillMyCode.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author agust
 */
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean status=false;
    private Long devId;
    private Long accountantId;
    private String nombreDev;
    private String nombreAccountant;
    private Double valoracion;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String comentario;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String respuesta;
    @Temporal(TemporalType.DATE)
    private Date fechaComentario;
    @Temporal(TemporalType.DATE)
    private Date fechaRespuesta;
}

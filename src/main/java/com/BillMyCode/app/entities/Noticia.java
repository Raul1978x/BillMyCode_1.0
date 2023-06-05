package com.BillMyCode.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo ;
    @Lob
    private String contenido;
    @OneToOne
    private Image image;
    @Column(name = "hora_subida")
    private LocalDateTime horaSubida;
}
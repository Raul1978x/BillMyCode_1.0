/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BillMyCode.app.entidades;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 *
*/
@Data
@Entity
public class Contador extends Usuario {
    
    private Double reputacion; 
    @OneToMany
    private List<Comentario> comentario;
    private String matricula;
    private List<String> especializaciones;
    private Double honorarios;
    private Boolean status;
    @OneToMany
    private List<Developer> developer;
    
    
    
    
    
}

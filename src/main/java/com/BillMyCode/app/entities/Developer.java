
package com.BillMyCode.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * @author agust
*/
@Getter
@Setter
@Entity
public class Developer extends User {

    protected Double salario;
    protected String seniority;
    protected String especialidad;
    @Lob
    protected String descripcion;
    @OneToOne
    protected Comment comentario;
    @ManyToMany
    protected List<Company> empresas;
    @OneToOne
    protected Accountant accountant;
    @OneToMany
    protected List<Quest> questList;
}
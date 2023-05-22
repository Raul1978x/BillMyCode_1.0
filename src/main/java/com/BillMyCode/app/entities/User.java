package com.BillMyCode.app.entities;

/**
 * @author agust
 */

import com.BillMyCode.app.enumerations.Rol;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String genero;
    protected String nacionalidad;
    protected String password;
    protected String telefono;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    protected Date fechaNacimiento;
    @OneToOne
    protected Image image;
    @Enumerated(EnumType.STRING)
    protected Rol rol;

}

package com.BillMyCode.app.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author agust
 */
@Getter
@Setter
@Entity
public class Admin extends Usuario{
    private String telefono; //Proponer añadir "telefono" a la clase usuario para aprovechar la herencia
}

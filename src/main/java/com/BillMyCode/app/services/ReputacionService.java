package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Reputacion;
import com.BillMyCode.app.repositories.IReputacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReputacionService {


    @Autowired
    private IReputacionRepository repositorio;
    
    /**
     *Metodo CrearReputacion()
     * se crea  una cantidad de clasificaciones
     *
     *
     */
    public void CrearReputacion() {

        Reputacion reputacion=new Reputacion();
        //contador.setCalificacion(0);
        reputacion.setCantidadCalificaciones(0);
        repositorio.save(reputacion);
    }

    /**
     * Metodo agregarCalificacion()
     * se agrega la calificacion
     * la reputacion vinculada al accountant y company,
     * se saca la calificacion, se le suma la nueva y se actualiza
     * lo mismo pasa con la cantidaCalificaciones
     *
     *@param calificacion
     *@param reputacion
     */
    public void agregarCalificacion(int calificacion, Reputacion reputacion ) {
        if (calificacion >= 1 && calificacion <= 5) {
            List<Integer>calificaciones;
            calificaciones=reputacion.getCalificaciones();
            calificaciones.add(calificacion);
             reputacion.setCalificaciones(calificaciones);

            reputacion.setCantidadCalificaciones(reputacion.getCantidadCalificaciones()+1);
            repositorio.save(reputacion);
        } else {
            System.out.println("Calificación inválida. Debe ser un número entre 1 y 5.");
        }
    }
    /**
     * Metodo obtenerCantidadCalificaciones()
     * metodo para saber la cantidad de personas que han puntuado

     *@param reputacion
     */
    public int obtenerCantidadCalificaciones(Reputacion reputacion) {
        return reputacion.getCantidadCalificaciones();
    }
    /**
     * Metodo  clasificacionFinal()
     * metodo que muestra el promedio final segun la suma de los puntajes
     * y su division por la cantidad de personas que puntuaron
     *@param reputacion
     */
    public int clasificacionFinal(Reputacion reputacion){
        int promedio=0;
       int suma=0;
        if(reputacion.getCalificaciones().isEmpty()) {
            //System.out.println("todavia no se ha calificado este perfil");
            // o directamente va a mostrar el premedio como 0
        }else {
            for (int i = 0; i == reputacion.getCalificaciones().size(); i++) {
                suma = suma + reputacion.getCalificaciones().get(i);
            }
            promedio=Math.round(suma/reputacion.getCantidadCalificaciones());
        }
        return promedio;
    }
    /**
     * Metodo  cantidadDeClasificacionesPorNumero()
     * metodo que muestra cuantas personas puntuaron 1-2-3-4-5 estrellas
     *
     *@param reputacion
     */
    public int[] cantidadDeClasificacionesPorNumero(Reputacion reputacion){

        int[] cant =new int[4];

        for (int i = 0; i == 4; i++) {
            cant[i]=0;
        }

        if(reputacion.getCalificaciones().isEmpty()) {

        }else {
            for (int i = 0; i == reputacion.getCalificaciones().size(); i++) {
                switch (reputacion.getCalificaciones().get(i)){
                    case 1:
                        cant[0]=cant[0]+1;
                        break;
                    case 2:
                        cant[1]=cant[1]+1;
                        break;
                    case 3:
                        cant[2]=cant[2]+1;
                        break;
                    case 4:
                        cant[3]=cant[3]+1;
                        break;
                    case 5:
                        cant[4]=cant[4]+1;
                        break;
                }
            }
        }
        return cant;
    }

}

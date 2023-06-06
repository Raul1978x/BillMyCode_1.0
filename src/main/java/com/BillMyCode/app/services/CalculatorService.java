package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Developer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {

    /**
     *Metodo CalcularMonotributo()
     * se ingresa la facturacionAnual y segun rango entra en los distintos monotributos
     * segun el nivel de monotributo(ej:A) se le dira cuanto es el
     * importe de los impuestos que tienen que pagar
     *
     * @param  facturacionAnual
     */
    public Double CalcularMonotributo (Double facturacionAnual){
        Double impuesto=0.0;
        String opcion="";
        opcion=tipoMonotributo(opcion,facturacionAnual);

        switch (opcion){
            case "A":
                impuesto= 5750.75;

                break;
            case "B":
                impuesto=6430.38;

                break;
            case "C":
                impuesto= 7351.10;
                break;
            case "D":
                impuesto= 9245.06;
                break;
            case "E":
                impuesto= 12776.61;
                break;
            case "F":
                impuesto= 15712.40;
                break;
            case "G":
                impuesto= 18347.44;
                break;
            case "H":
                impuesto= 31347.16;
                break;
            case "I":
                impuesto= 38464.47;
                break;
            case "J":
                impuesto= 44390.28;
                break;
            case "K":
                impuesto= 50717.92;
                break;
        }

        return impuesto;
    }
    public String tipoMonotributo(String opcion, Double facturacionAnual){
        if(facturacionAnual<= 999657.23){
            opcion="A";

        }else if(facturacionAnual<= 1485976.96){
            opcion="B";

        }else if(facturacionAnual<=2080367.73){
            opcion="C";
        }else if(facturacionAnual<= 2583720.42){
            opcion="D";
        }else if(facturacionAnual<= 3042435.05){
            opcion="E";
        }else if(facturacionAnual<= 3803043.82){
            opcion="F";
        }else if(facturacionAnual<= 4563652.57){
            opcion="G";
        }else if(facturacionAnual<= 5650236.51){
            opcion="H";
        }else if(facturacionAnual<= 6323918.55){
            opcion="I";
        }else if(facturacionAnual<= 7247514.92){
            opcion="J";
        }else if(facturacionAnual<= 8040721.19){
            opcion="K";
        }
return opcion;
    }

    /**
     * Metodo comparacionPromedio() el dev ingresa su salario,su seniority y especialidad y segun este
     *    se compara con el promedio de todos los salarios de nivel de seniority y especialidad
     *
     * @param  especialidad
     * @param  seniority
     * @param  salario
     * @param  developers
     */
    public String comparacionPromedio(Double salario,List<Developer> developers,String seniority,String especialidad){

        String valoracionSalario="";
       Double promedio= Double.valueOf(calcularPromedio(developers,seniority,especialidad));

        if (promedio<salario){
            valoracionSalario=" encima";
        }else {
            valoracionSalario=" debajo";
        }
        String mensaje=" "+ valoracionSalario+" del promedio";
       return mensaje;
//       return   "tu salario se encuentra por encima/ debajo del promedio";
    }


    /**
     * Metodo calcularPromedio() calcula el promedio segun el nivel de seniority y su especialidad
     *
     *
     * @param  seniority
     * @param  especialidad
     * @param  developers
     */
    public Double calcularPromedio(List<Developer> developers,String seniority,String especialidad){
        Double promedio=0.0;
        int count=0;
        for(Developer dev: developers){
            if(dev.getSeniority().equals(seniority)){
                if(dev.getEspecialidad().equals(especialidad)){
                    promedio=promedio+dev.getSalario();
                    count=count+1;
                }
            }
        }
        promedio=promedio/count;
  /*      String mensaje="segun los developers, "+ seniority+", con especialidad en "+especialidad
                +" el salario promedio de entre "+count+ " es de: "+promedio;*/
        return promedio ;
    }
/** cuenta cantidad de developers con el mism seniority y especialidad*/
    public int contarPromedio(List<Developer> developers,String seniority,String especialidad){

        int count=0;
        for(Developer dev: developers){
            if(dev.getSeniority().equals(seniority)){
                if(dev.getEspecialidad().equals(especialidad)){
                    count=count+1;
                }
            }
        }

        return count ;
    }
    /**
     * Metodo calcularPromedio() calcula el promedio segun el nivel de seniority y su especialidad
     *
     *
     * @param  seniority
     * @param  especialidad
     * @param  developers
     */
    public int countDevSeniorityEspecialidad(List<Developer> developers,String seniority,String especialidad){
        int count=0;
        for(Developer dev: developers){
            if(dev.getSeniority().equals(seniority)){
                if(dev.getEspecialidad().equals(especialidad)){

                    count=count+1;
                }
            }
        }


        return count ;
    }

    /**
     * Metodo buscarElSueldoMasAlto() calcula el promedio segun el nivel de seniority y su especialidad
     *
     *
     * @param  seniority
     * @param  especialidad
     * @param  developers
     */
    public String buscarElSueldoMasAlto(List<Developer> developers,String seniority,String especialidad){
        Double sueldoMasAlto=0.0;
        for(Developer dev: developers) {
            if (dev.getSeniority().equals(seniority)) {
                if (dev.getEspecialidad().equals(especialidad)) {
                    if (dev.getSalario() > sueldoMasAlto) {
                        sueldoMasAlto = dev.getSalario();
                    }
                }
            }
        }
        String mensaje= " "+sueldoMasAlto;
        return mensaje;
    }

    public Double buscarElSueldoMasAltoNewData(List<Developer> developers,String seniority,String especialidad, Double salario){
        Double sueldoMasAlto=0.0;
        for(Developer dev: developers) {
            if (dev.getSeniority().equals(seniority)) {
                if (dev.getEspecialidad().equals(especialidad)) {
                    if (dev.getSalario() > sueldoMasAlto) {
                        sueldoMasAlto = dev.getSalario();
                    }
                }
            }
        }
        if(sueldoMasAlto < salario){
            sueldoMasAlto = salario;
        }

        return sueldoMasAlto;
    }




}


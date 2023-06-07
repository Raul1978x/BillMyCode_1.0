package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.services.CalculatorService;
import com.BillMyCode.app.services.DeveloperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/calculadora")
public class CalculatorController {

    @Autowired
    DeveloperService developerService;

    @Autowired
    CalculatorService calculatorService;

    @GetMapping("/calcular")
    public String calculator(HttpSession request, ModelMap model) throws MiException {
        Developer logueado= (Developer)  request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return("pedido_de_datos.html");
    }
    @GetMapping("/calculadoraCargado")
    public String calculatorRespuesta(HttpSession request, ModelMap model) throws MiException {
        Developer logueado= (Developer)  request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "calculator.html";
    }
    @PostMapping("/CalculadoraRespuesta")
    public String calculatorResults(  HttpSession request,  ModelMap model) throws MiException {
        Developer logueado= (Developer)  request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        List<Developer>developers= developerService.listDevelopers();

    String respuesta1 = calculatorService.buscarElSueldoMasAlto(developers, logueado.getSeniority(), logueado.getEspecialidad()).toString();
    String respuesta2 = calculatorService.CalcularMonotributo(logueado.getSalario());
   // model.put("facturacionAnual", logueado.getSalario()*12);
    String respuesta3 = calculatorService.calcularPromedio(developers, logueado.getSeniority(), logueado.getEspecialidad()).toString();
    String respuesta4 = calculatorService.comparacionPromedio(logueado.getSalario(), developers, logueado.getSeniority(), logueado.getEspecialidad());

    model.put("respuesta1", respuesta1);
    model.put("respuesta2", respuesta2);
    model.put("respuesta3", respuesta3);
    model.put("respuesta4", respuesta4);

    String count = "" + calculatorService.contarPromedio(developers, logueado.getSeniority(), logueado.getEspecialidad());
    model.put("count", count);

    String categoria= calculatorService.tipoMonotributo(logueado.getSalario());
    model.put("categoria", categoria);

        return "calculator.html";
    }

    @GetMapping("/answerTwoNewData")
    public String getViewNewData (HttpSession request, ModelMap model){

        Developer logueado= (Developer)  request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        return "CalculatorNewData.html";
    }
    @PostMapping("/respuestaNewData")
    public String calculatorResultsNewData(  @RequestParam Double salario, HttpSession request,
                                            @RequestParam String seniority, @RequestParam String especialidad, ModelMap model) throws MiException {

        Developer logueado= (Developer)  request.getAttribute("sessionuser");
        model.put("logueado", logueado);

        List<Developer> developers= developerService.listDevelopers();
        String respuesta1= calculatorService.buscarElSueldoMasAltoNewData(developers,seniority,especialidad,salario).toString();
        String respuesta2= calculatorService.CalcularMonotributo(salario);
        String respuesta3= calculatorService.calcularPromedio(developers, seniority,especialidad).toString();
        String respuesta4= calculatorService.comparacionPromedio(salario, developers,  seniority,especialidad);

        model.put("respuesta1", respuesta1);
        model.put("respuesta2", respuesta2);
        model.put("respuesta3", respuesta3);
        model.put("respuesta4", respuesta4);

        String count= ""+ calculatorService.contarPromedio(developers, seniority, especialidad);
        model.put("count",count);

        String categoria=calculatorService.tipoMonotributo(salario);
        model.put("categoria",categoria);

        return "CalculatorNewData.html";
    }
}

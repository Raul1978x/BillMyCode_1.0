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
        return("calculator.html");
    }
    @PostMapping("/CalculadoraRespuesta")
    public String calculatorResults( @RequestParam Double facturacionAnual, HttpSession request,  ModelMap model) throws MiException {
        Developer logueado= (Developer)  request.getAttribute("sessionuser");
        model.put("logueado", logueado);
        List<Developer>developers= developerService.listDevelopers();

        String respuesta1= calculatorService.buscarElSueldoMasAlto(developers, logueado.getSeniority(), logueado.getEspecialidad());
        String respuesta2= calculatorService.CalcularMonotributo(facturacionAnual).toString();
        model.put("facturacionAnual",facturacionAnual);
        String respuesta3= calculatorService.calcularPromedio(developers, logueado.getSeniority(), logueado.getEspecialidad()).toString();
        String respuesta4= calculatorService.comparacionPromedio(logueado.getSalario(), developers, logueado.getSeniority(), logueado.getEspecialidad());
       
        model.put("respuesta1", respuesta1);
        model.put("respuesta2", respuesta2);
        model.put("respuesta3", respuesta3);
        model.put("respuesta4", respuesta4);

        return("calculator.html");
    }
    @PostMapping("/respuestaNewData")
    public String calculatorResultsNewData( @RequestParam ("facturacionAnual") Double facturacionAnual, @RequestParam ("salario") Double salario,
                                            @RequestParam ("seniority") String seniority, @RequestParam ("especialidad") String especialidad, ModelMap model) throws MiException {

        List<Developer> developers= developerService.listDevelopers();
        String respuesta1= calculatorService.buscarElSueldoMasAlto(developers,seniority,especialidad);
        String respuesta2= ""+ calculatorService.CalcularMonotributo(facturacionAnual);
        String respuesta3= calculatorService.calcularPromedio(developers, seniority,especialidad).toString();
        String respuesta4= calculatorService.comparacionPromedio(salario, developers,  seniority,especialidad);

        model.addAttribute("respuesta1", respuesta1);
        model.addAttribute("respuesta2", respuesta2);
        model.addAttribute("respuesta3", respuesta3);
        model.addAttribute("respuesta4", respuesta4);

        return("calculator.html");
    }
}

package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Company;
import com.BillMyCode.app.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public String getAllCompanies(ModelMap model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "companies";
    }

    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable Long id, ModelMap model) {
        Optional<Company> company = companyService.getCompanyById(id);
        company.ifPresent(value -> model.addAttribute("company", value));
        return "company";
    }

    @PostMapping
    public String createCompany(@ModelAttribute Company company, ModelMap model) {
        Company createdCompany = companyService.createCompany(company);
        model.addAttribute("company", createdCompany);
        return "company";
    }

    @PutMapping("/{id}")
    public String updateCompany(@PathVariable Long id, @ModelAttribute Company company, ModelMap model) {
        company.setId(id);
        companyService.updateCompany(company);
        model.addAttribute("company", company);
        return "company";
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }
}

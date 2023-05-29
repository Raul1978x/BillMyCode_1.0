package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Admin;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.AdminService;
import com.BillMyCode.app.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/imagen")
public class ImagenRestController {
    @Autowired
    private DeveloperService developerService;
    @Autowired
    private AccountantService accountantService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable Long id) {
        Developer developer = developerService.searchDeveloperById(id);

        byte[] imagen = developer.getImage().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/perfil-accountant/{id}")
    public ResponseEntity<byte[]> imagenAccountant(@PathVariable Long id) {
        Accountant accountant = accountantService.searchAccounterById(id);

        byte[] imagen = accountant.getImage().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/perfil-admin/{id}")
    public ResponseEntity<byte[]> imagenAdmin(@PathVariable Long id) {
        Admin admin = adminService.searchAdminById(id);

        byte[] imagen = admin.getImage().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/perfil-usuario/{id}")
    public ResponseEntity<byte[]> imagenUser(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (roles.contains("ROLE_ADMIN")) {
            Admin admin = adminService.searchAdminById(id);
            byte[] imagen = admin.getImage().getContenido();
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        } else if (roles.contains("ROLE_DEV")) {
            Developer developer = developerService.searchDeveloperById(id);
            byte[] imagen = developer.getImage().getContenido();
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        } else {
            Accountant accountant = accountantService.searchAccounterById(id);
            byte[] imagen = accountant.getImage().getContenido();
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

        }
    }
}

package com.BillMyCode.app.controllers;

import com.BillMyCode.app.entities.Accountant;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.services.AccountantService;
import com.BillMyCode.app.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imagen")
public class ImagenRestController {
    @Autowired
    private DeveloperService developerService;
    @Autowired
    private AccountantService accountantService;

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

}

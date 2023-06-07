package com.BillMyCode.app.services;


import com.BillMyCode.app.entities.*;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.INoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {
    @Autowired
    private INoticiaRepository noticiaRepository;
    @Autowired
    private ImageService imageService;

    public NoticiaService(INoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public void crearNoticia(MultipartFile archivo, String titulo , String contenido, LocalDateTime horaSubida){

        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setContenido(contenido);
        noticia.setHoraSubida(horaSubida);

if(archivo != null && !archivo.isEmpty()) {
    Image image = imageService.save(archivo);

    noticia.setImage(image);
    noticiaRepository.save(noticia);
}else{
    Image image = imageService.ImagenNoticia();
    noticia.setImage(image);
    noticiaRepository.save(noticia);
}


    }
    public List<Noticia> listarNoticia(){
        List<Noticia> noticia = new ArrayList<>();

        noticia = noticiaRepository.findAll();
        return noticia;
    }



    public Noticia searchNoticiaId(Long id) {
        return noticiaRepository.findById(id).get();
    }
    public void eliminarNoticia(Long id) {
        noticiaRepository.deleteById(id);
    }
    @Transactional
    public void editNoticia(Long id,
                                MultipartFile archivo,
                                String titulo,
                                String contenido


    ) throws MiException, ParseException {

        Optional<Noticia> respuesta = noticiaRepository.findById(id);

        validate(titulo,contenido);
        if (respuesta.isPresent()) {
            Noticia result = respuesta.get();



            result.setTitulo(titulo);
            result.setContenido(contenido);
            result.setHoraSubida( LocalDateTime.now());



            if (archivo != null) {
                Image image = imageService.save(archivo);
                result.setImage(image);
            }

            noticiaRepository.save(result);
        }
    }
    public void validate(
                         String contenido,
                         String titulo


    ) throws MiException {

        if (titulo.isBlank() || titulo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (contenido.isBlank() || contenido.isEmpty()) {
            throw new MiException("El contenido no puede ser nulo o estar vacio");
        }


    }
}

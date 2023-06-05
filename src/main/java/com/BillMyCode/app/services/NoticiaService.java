package com.BillMyCode.app.services;


import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.entities.Noticia;
import com.BillMyCode.app.repositories.INoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}else{
    Image image = imageService.ImagenNoticia();
    noticia.setImage(image);
}

        noticiaRepository.save(noticia);

    }
    public List<Noticia> listarNoticia(){
        List<Noticia> noticia = new ArrayList<>();

        noticia = noticiaRepository.findAll();
        return noticia;
    }



    public Noticia searchNoticiaId(Long id) {
        return noticiaRepository.findById(id).get();
    }
}

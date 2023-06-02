package com.BillMyCode.app.services;


import com.BillMyCode.app.entities.Noticia;
import com.BillMyCode.app.repositories.INoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticiaService {
    @Autowired
    private INoticiaRepository noticiaRepository;

    public NoticiaService(INoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public void CrearNoticia(Long id, String titulo , String contenido ){

        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setContenido(contenido);
        noticiaRepository.save(noticia);

    }
    public List<Noticia> ListarNoticia(){
        List<Noticia> noticia = new ArrayList<>();
        noticia = noticiaRepository.findAll();
        return noticia;
    }
    public void EliminarNoticia(Long id) {
        noticiaRepository.deleteById(id);
    }
}
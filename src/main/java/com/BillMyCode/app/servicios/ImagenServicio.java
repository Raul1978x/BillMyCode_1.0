package com.BillMyCode.app.servicios;

import com.BillMyCode.app.entidades.Imagen;
import com.BillMyCode.app.repositorios.IImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagenServicio {

    @Autowired
    private IImagenRepositorio repositorio;

    @Transactional(readOnly = true)
    public Imagen buscarImagenById(Long id){
        return repositorio.findById(id).get();
    }

    @Transactional
    public void borrarImagenById(long id){
        repositorio.deleteById(id);
    }

    @Transactional
    public Imagen guardar(MultipartFile archivo){
        if (archivo != null){
            try {
                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                repositorio.save(imagen);

                return imagen;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}

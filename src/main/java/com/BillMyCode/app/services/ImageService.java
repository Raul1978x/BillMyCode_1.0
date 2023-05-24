package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.repositories.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private IImageRepository repository;

    @Transactional(readOnly = true)
    public Image buscarImagenById(Long id){
        return repository.findById(id).get();
    }

    @Transactional
    public void borrarImagenById(long id){
        repository.deleteById(id);
    }

    @Transactional
    public Image save(MultipartFile archivo){
        if (archivo != null){
            try {
                Image image = new Image();

                image.setMime(archivo.getContentType());
                image.setNombre(archivo.getName());
                image.setContenido(archivo.getBytes());

                repository.save(image);

                return image;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}

package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Image;
import com.BillMyCode.app.exceptions.MiException;
import com.BillMyCode.app.repositories.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private IImageRepository repository;

    /**
     * Metodo buscarImagenById: Trae una imagen segun un ID, solo para lectura
     *
     * @param id: id pasado por parametro
     *
     * @return El archivo Image
     */
    @Transactional(readOnly = true)
    public Image buscarImagenById(Long id){
        return repository.findById(id).get();
    }

    /**
     * Metodo buscarImagenById: Borra una imagen segun un ID
     *
     * @param id: id pasado por parametro
     */
    @Transactional
    public void borrarImagenById(long id){
        repository.deleteById(id);
    }

    /**
     * Metodo save: Crea un objeto image con el archivo pasado por parametro
     *
     * @param archivo: Imagen cargada por el usuario
     *
     * @return null si archivo==null si no un objeto image
     */
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
    @Transactional
    public Image update(Long id, MultipartFile archivo) {
        if (archivo != null) {
            try {
                Optional<Image> optionalImage = repository.findById(id);
                if (optionalImage.isPresent()) {
                    Image image = optionalImage.get();

                    image.setMime(archivo.getContentType());
                    image.setNombre(archivo.getName());
                    image.setContenido(archivo.getBytes());

                    return repository.save(image);
                } else {
                    throw new RuntimeException("No se encontró una imagen con el ID proporcionado");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("El archivo de imagen es nulo");
        }
    }
    public Image ImagenNoticia(){
        try {
            String defaultImagePath = "src/main/resources/static/images/defaultnoticia.jpg";
            File defaultImageFile = new File(defaultImagePath);
            byte[] defaultImageData = Files.readAllBytes(defaultImageFile.toPath());

            Image defaultImage = new Image();
            defaultImage.setMime("image/jpg");
            defaultImage.setNombre("defaultnoticia.jpg");
            defaultImage.setContenido(defaultImageData);

            repository.save(defaultImage);

            return defaultImage;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la imagen predeterminada.", e);
        }
    }
}

package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;

    /**
     * Metodo Comment: Actualiza un comentario de algun usuario segun la id
     *
     * @param id
     * @param comentario: Comentario nuevo
     *
     * @return
     */
    @Transactional
    public Comment updateComment(Long id, String comentario) {
        Optional<Comment> respuesta = commentRepository.findById(id);

        if (respuesta.isPresent()) {
            Comment result = respuesta.get();
            result.setComentario(comentario);
            result.setFecha(new Date());
            return commentRepository.save(result);
        }
        return null;
    }

    /**
     * Metodo createComment: Crea un objeto Comment a partir de un comentario pasado por parametro
     *
     * @param comentario
     *
     * @return
     */
    @Transactional
    public Comment createComment(String comentario) {
        Comment comment = new Comment();
        comment.setComentario(comentario);
        comment.setFecha(new Date());
        return commentRepository.save(comment);
    }

    /**
     * Método deleteCommentById: Borra un comentario segun una id
     *
     * @param id
     */
    @Transactional
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

    /**
     * Metodo searchAllComment: Devuelve una lista de todos los comentarios en el repositorio ICommentRepository
     *
     * @return: Lista de Comment
     */
    @Transactional(readOnly = true)
    public List<Comment> searchAllComment(){
        return commentRepository.findAll();
    }

    /**
     * Método deleteCommentById: Busca un comentario segun una id
     *
     * @param id
     */
    @Transactional(readOnly = true)
    public Comment searchCommentById(long id){
        return commentRepository.findById(id).get();
    }
}
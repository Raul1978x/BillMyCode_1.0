package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.AnswerAndQuestion;
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
            result.setFechaComentario(new Date());
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
        comment.setFechaComentario(new Date());
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

    @Transactional
    public List<Comment> getAllComentarios(){
        List<Comment> comentarios = commentRepository.findAll();
        return comentarios;
    }

    @Transactional
    public List<Comment> getAccountantComment(Long id){
        List<Comment> comentarios = commentRepository.searchAllCommentByAccountant(id);
        return comentarios;
    }

    @Transactional
    public Comment crearComentario(String comentario, String nombreDev, String nombreAcc, Long idDev, Long idAcc){
        Comment comment = new Comment();
        comment.setComentario(comentario);
        comment.setFechaComentario(new Date());
        comment.setNombreDev(nombreDev);
        comment.setNombreAccountant(nombreAcc);
        comment.setDevId(idDev);
        comment.setAccountantId(idAcc);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment crearRespuesta(String respuesta, Long id){
        Comment res = commentRepository.findById(id).get();
        res.setComentario(respuesta);
        res.setFechaRespuesta(new Date());
        res.setStatus(true);
        return commentRepository.save(res);
    }
}
package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;

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
    @Transactional
    public Comment createComment(String comentario) {
        Comment comment = new Comment();
        comment.setComentario(comentario);
        comment.setFecha(new Date());
        return commentRepository.save(comment);
    }
}
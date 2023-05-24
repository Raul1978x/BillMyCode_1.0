package com.BillMyCode.app.services;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final ICommentRepository commentRepository;

    @Autowired
    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment createComment(String comentario) {
        Comment comment = new Comment();
        comment.setComentario(comentario);
        comment.setFecha(new Date());
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, String comentario) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setComentario(comentario);
            return commentRepository.save(comment);
        }
        return null;
    }

    @Transactional
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
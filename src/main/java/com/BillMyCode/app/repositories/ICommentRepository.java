package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.accountantId = :accountantId")
    public List<Comment> searchAllCommentByAccountant(@Param("accountantId") Long accountantId);
}

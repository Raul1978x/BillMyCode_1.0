package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Developer;
import com.BillMyCode.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    /*@Query("SELECT u FROM User u WHERE u.email = :email")
    public User seachByEmail(@Param("email") String email);*/

}

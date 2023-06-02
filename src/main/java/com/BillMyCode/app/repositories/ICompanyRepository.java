package com.BillMyCode.app.repositories;

import com.BillMyCode.app.entities.Comment;
import com.BillMyCode.app.entities.Company;
import com.BillMyCode.app.exceptions.MiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long> {


}

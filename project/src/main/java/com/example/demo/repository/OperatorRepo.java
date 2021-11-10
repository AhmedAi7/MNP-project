package com.example.demo.repository;

import com.example.demo.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepo extends JpaRepository<Operator,String> {
    Operator getOperatorByPrefix(String prefix); //get the operator by its prefix

}

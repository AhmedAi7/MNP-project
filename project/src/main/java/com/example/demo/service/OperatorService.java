package com.example.demo.service;

import com.example.demo.interfaces.IOperatorService;
import com.example.demo.model.Operator;
import com.example.demo.repository.OperatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorService implements IOperatorService {
    @Autowired
    OperatorRepo operatorRepo;

    //if operator exists by its header
    public Boolean isExist (String header){
        try {
            return operatorRepo.existsById(header);
        }
        catch (Exception e){
            return false;
        }
    }
    //get operator prefix by its header
    public String getPrefix(String header){
        try {
        Operator operator = operatorRepo.getById(header);
        return operator.getPrefix();
        }
        catch (Exception e){
            return null;
        }
    }
    //get operator header by its prefix
    public String getHeader(String prefix){
        try {
            Operator operator = operatorRepo.getOperatorByPrefix(prefix);
            return operator.getHeader();
        }
        catch (NullPointerException e){//Not found
            return null;
        }
    }
}

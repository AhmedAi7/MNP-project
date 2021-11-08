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

    //if operator exists
    public Boolean isExist (String header){
        if (header==null)
            return false;
        return operatorRepo.existsById(header);
    }
    //get operator prefix
    public String getPrefix(String header){
        Operator operator = operatorRepo.getById(header);
        return operator.getPrefix();
    }
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

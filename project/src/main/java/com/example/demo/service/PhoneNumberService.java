package com.example.demo.service;

import com.example.demo.exceptions.GlobalExceptions;
import com.example.demo.interfaces.IOperatorService;
import com.example.demo.interfaces.IPhoneNumberService;
import com.example.demo.model.PhoneNumber;
import com.example.demo.repository.PhoneNumberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService implements IPhoneNumberService {

    @Autowired
    PhoneNumberRepo phoneNumberRepo;
    @Autowired
    IOperatorService operatorService;
    @Autowired
    GlobalExceptions globalExceptions;

    //Check if phone number exist in the table (have a previous accepted request)
    public Boolean isExist(String num){

        try {
            return phoneNumberRepo.existsById(num);
        }
        catch (Exception e){
            return false;
        }
    }

    // Get the current operator of the number
    public String getOperator(String num){
        //check if the phone number has switched it's operator before (had a successful operator and saved in phone number table)
        if (isExist(num))
        {
            return phoneNumberRepo.getById(num).getOperator(); // return the recipient of the last accepted request
        }
        else {
            //Phone numbers operator hasn't changed before .. use the prefix to get the current operator
            String numberPrefix=num.substring(0,3); //the prefix of the phone number (first 3 digits)
            return operatorService.getHeader(numberPrefix); //return the header (operator name)
        }
    }
    public void setOperator(String num, String operator){
        //Check if phone number is ported before (Exists in phone number table)
        if (isExist(num)){
            PhoneNumber phoneNumber=phoneNumberRepo.getById(num);
            phoneNumber.setOperator(operator); // update the current operator
            phoneNumberRepo.save(phoneNumber);
        }
        else {
            PhoneNumber phoneNumber=new PhoneNumber(num,operator); // create new phone number record
            phoneNumberRepo.save(phoneNumber); //insert into ported numbers table
        }
    }
    public void Validate(String number) throws IllegalArgumentException{
        //get the current operator of the phone number
        String holder = getOperator(number);
        //Check if the phone number is wrong (wrong prefix)
        if (holder==null){
            throw new IllegalArgumentException (globalExceptions.errWrongPhoneNumber);
        }
    }
}

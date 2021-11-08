package com.example.demo.interfaces;

public interface IPhoneNumberService {

    //Check if phone number exist in the table (have a previous accepted request)
    public Boolean isExist(String num);
    // Get the current operator of the number (the recipient of the last accepted request)
    public String getOperator(String num);

    //Set Operator for phone number
    public void setOperator(String num,String operator);
}

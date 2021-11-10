package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phone_number")
public class PhoneNumber {
    @Id
    String number;
    String operator;

    public PhoneNumber(String num, String operator) {
        this.number = num;
        this.operator = operator;
    }

    public PhoneNumber() {
    }
    public PhoneNumber(PhoneNumber phoneNumber){
        this.number = phoneNumber.number;
        this.operator = phoneNumber.operator;
    }
    public String getNumber() {
        return number;
    }

    public void setNum(String num) {
        this.number = num;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

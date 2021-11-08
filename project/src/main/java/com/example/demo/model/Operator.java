package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operator")
public class Operator {

    @Id
    String header;
    String prefix;

    public Operator(String header, String prefix) {
        this.header = header;
        this.prefix = prefix;
    }

    public Operator() {
    }

    public Operator(Operator operator){
        this.header=operator.getHeader();
        this.prefix=operator.getPrefix();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

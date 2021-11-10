package com.example.demo.interfaces;

public interface IOperatorService {
    public Boolean isExist (String header);
    public String getPrefix(String header);
    public String getHeader(String prefix);
}

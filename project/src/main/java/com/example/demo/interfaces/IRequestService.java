package com.example.demo.interfaces;

import com.example.demo.model.Request;
import com.example.demo.payload.RequestPayload;

public interface IRequestService {

    public boolean addRequest(String header, RequestPayload requestPayload);
    public boolean isPending(String header, RequestPayload requestPayload);
    public boolean accept(Long requestID);
    public boolean reject(Long requestID);
    public void cancel(Request request);
    public String getDonor (Long requestID);
    public String getRecipient (Long requestID);
    public String getPhoneNumber(Long requestID);
}

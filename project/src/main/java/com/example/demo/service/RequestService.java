package com.example.demo.service;

import com.example.demo.interfaces.IRequestService;
import com.example.demo.model.Request;
import com.example.demo.payload.RequestPayload;
import com.example.demo.payload.StatusEnum;
import com.example.demo.repository.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService implements IRequestService {
    @Autowired
    RequestRepo requestRepo;

    public boolean addRequest(String header, RequestPayload requestPayload) {
        Request request = new Request(header,
                requestPayload.getDonor(),
                requestPayload.getNumber());
        try {
            requestRepo.save(request);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    // check if the request is pending
    public boolean isPending(String header, RequestPayload requestPayload){
        return requestRepo.existsByRecipientAndDonorAndNumberAndStatus(header,
                requestPayload.getDonor(),
                requestPayload.getNumber(),
                StatusEnum.PENDING);
    }
    //Accept Request
    public boolean accept(Long requestID){
        Request request = requestRepo.getById(requestID);
        if (!request.getStatus().equals(StatusEnum.PENDING)) //Request exists but Not a Pending request
            return false;
        request.setStatus(StatusEnum.ACCEPTED); //update the status of the request to ACCEPTED
        requestRepo.save(request);
        return true;
    }

    //Reject Request
    public boolean reject(Long requestID) {
        Request request = requestRepo.getById(requestID);
        if (!request.getStatus().equals(StatusEnum.PENDING)) //Request exists but Not a Pending request
            return false;
        request.setStatus(StatusEnum.REJECTED); //update the status of the request to REJECTED
        requestRepo.save(request);
        return true;
    }
    // Cancel request
    public void cancel(Request request){
        request.setStatus(StatusEnum.CANCELED);
        requestRepo.save(request);
    }

    // Get The Donor of request
    public String getDonor (Long requestID){
        if (!requestRepo.existsById(requestID)) //No Request with This ID
            return null;
        Request request= requestRepo.getById(requestID);
        return request.getDonor();
    }
    // Get The Recipient of request
    public String getRecipient (Long requestID){
        if (!requestRepo.existsById(requestID)) //No Request with This ID
            return null;
        Request request= requestRepo.getById(requestID);
        return request.getRecipient();
    }
    // Get The Phone Number of request
    public String getPhoneNumber(Long requestID)
    {
        if (!requestRepo.existsById(requestID)) //No Request with This ID
            return null;
        Request request= requestRepo.getById(requestID);
        return request.getNumber();
    }
}

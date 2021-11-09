package com.example.demo.service;

import com.example.demo.interfaces.IRequestService;
import com.example.demo.model.Request;
import com.example.demo.payload.RequestPayload;
import com.example.demo.payload.StatusEnum;
import com.example.demo.repository.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService implements IRequestService {
    @Autowired
    RequestRepo requestRepo;

    //Save request in Request table
    public boolean addRequest(String header, RequestPayload requestPayload) {
        //Create new object of request record
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
        try {
            //is exists by Recipient, Donor, Number and Status
            return requestRepo.existsByRecipientAndDonorAndNumberAndStatus(header,
                    requestPayload.getDonor(),
                    requestPayload.getNumber(),
                    StatusEnum.PENDING);
        }
        catch (Exception e){
            return false;
        }
    }
    //Accept Request
    public boolean accept(Long requestID){
        Request request = requestRepo.getById(requestID);
        if (!request.getStatus().equals(StatusEnum.PENDING)) //Request exists but Not a Pending request
            return false;
        request.setStatus(StatusEnum.ACCEPTED); //update the status of the request to ACCEPTED
        try {
            requestRepo.save(request);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //Reject Request
    public boolean reject(Long requestID) {
        Request request = requestRepo.getById(requestID);
        if (!request.getStatus().equals(StatusEnum.PENDING)) //Request exists but Not a Pending request
            return false;
        request.setStatus(StatusEnum.REJECTED); //update the status of the request to REJECTED
        try {
            requestRepo.save(request);
            return true;
        }
        catch (Exception e){
            return false;
        }
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
    public List<Request> getAllRequests(String operator){
        try {
            // Get all requests that either the operator is a recipient or a donor of it
            // And all the accepted requests from any operators
            List<Request> requests =
                    requestRepo.findAllByDonorOrRecipientOrStatus(operator,operator,StatusEnum.ACCEPTED);
            return requests;
        }
        catch (Exception e){
            return null;
        }
    }
}

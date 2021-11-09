package com.example.demo.controller;

import com.example.demo.exceptions.GlobalExceptions;
import com.example.demo.interfaces.IOperatorService;
import com.example.demo.interfaces.IPhoneNumberService;
import com.example.demo.interfaces.IRequestService;
import com.example.demo.payload.RequestPayload;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OperatorController {
    @Autowired
    IOperatorService operatorService;
    @Autowired
    IPhoneNumberService phoneNumberService;
    @Autowired
    IRequestService requestService;
    @Autowired
    GlobalExceptions globalExceptions;

    @PostMapping("/SubmitRequest")
    public ResponseEntity<?> submitRequest(@RequestHeader String organization,@Valid @RequestBody RequestPayload requestPayload){
        Map<String, String> mp = new HashMap<>();

        //Validate the request first
        submitRequestValidation(organization,requestPayload);

        //Finally if the request is correct and not pending .. save the request in database
        if (requestService.addRequest(organization,requestPayload)){
            mp.put("message","Request Submitted Successfully");
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        else {
            mp.put("message","Error in Submitting the request");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/AcceptRequest")
    public ResponseEntity<?> acceptRequest(@RequestHeader String organization,@RequestParam Long requestID) {
        Map<String, String> mp = new HashMap<>();

        //Validate the request
        acceptOrRejectValidation(organization,requestID);

        if (requestService.accept(requestID)){ //accept the request
            String phoneNumber= requestService.getPhoneNumber(requestID); //get the phone number of this request
            String recipient = requestService.getRecipient(requestID); // the new operator of the number
            phoneNumberService.setOperator(phoneNumber,recipient); //Set the new operator to the number
            mp.put("message","Request Accepted Successfully");
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        else { //No pending requests with this ID (either ACCEPTED or REJECTED or CANCELED)
            mp.put("message",globalExceptions.errNotPending);
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/RejectRequest")
    public ResponseEntity<?> rejectRequest(@RequestHeader String organization,@RequestParam Long requestID){
        Map<String, String> mp = new HashMap<>();

        //Validate the request
        acceptOrRejectValidation(organization,requestID);

        if (requestService.reject(requestID)){ //Reject the request
            mp.put("message","Request Rejected Successfully");
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        else { //No pending requests with this ID (either ACCEPTED or REJECTED or CANCELED)
            mp.put("message",globalExceptions.errNotPending);
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
    }


    //Method to validate the request before submit it and throw an IllegalArgumentException if any error
    private void submitRequestValidation(String organization, RequestPayload requestPayload) throws IllegalArgumentException{
        String requestDonor=requestPayload.getDonor(); //Donor header
        String requestNumber=requestPayload.getNumber(); //requested phone number

        //check if donor header is not correct (Not Exists in Operator database table)
        Boolean isValidDonor= operatorService.isExist(requestDonor);
        if (!isValidDonor){
            throw new IllegalArgumentException(globalExceptions.errIncorrectDonorHeader);
        }
        //check the current operator of the number
        // (Must be different than the operator that sends the request and be the donor operator of the request)
        String currentOperator= phoneNumberService.getOperator(requestNumber); //get the current operator of the number
        if (currentOperator.equals(organization)){ //Current Operator is the same operator sends this request (request not valid)
            throw new IllegalArgumentException(globalExceptions.errPhoneNumberBelongToOperator);
        }
        if (!currentOperator.equals(requestDonor)){ //Current Operator isn't the donor one
            throw new IllegalArgumentException(globalExceptions.errPhoneNumberNotBelongToOperator);
        }

        //Check if it's already a pending request so it will rejected
        if (requestService.isPending(organization,requestPayload)){
            throw new IllegalArgumentException(globalExceptions.errIsPending);
        }

    }
    //Method to validate the request before accept or reject it and throw an IllegalArgumentException if any error
    private void acceptOrRejectValidation(String organization, Long requestID) throws IllegalArgumentException {
        String donor = requestService.getDonor(requestID); //Get the request donor header
        if (donor==null){ // No request with this ID
            throw new IllegalArgumentException(globalExceptions.errNoRequestID);
        }
        if (!donor.equals(organization)){ //check if the operator sends the request isn't the donor (can't accept the request)
            throw new IllegalArgumentException(globalExceptions.errNotTheDonor);
        }

    }
}

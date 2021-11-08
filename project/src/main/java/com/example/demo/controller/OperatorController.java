package com.example.demo.controller;

import com.example.demo.interfaces.IOperatorService;
import com.example.demo.interfaces.IPhoneNumberService;
import com.example.demo.interfaces.IRequestService;
import com.example.demo.payload.RequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.BindException;
import org.springframework.web.client.HttpClientErrorException;

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

    @PostMapping("/SubmitRequest")
    public ResponseEntity<?> submitRequest(@RequestHeader String organization,@Valid @RequestBody RequestPayload requestPayload){
        Map<String, String> mp = new HashMap<>();

        String requestDonor=requestPayload.getDonor(); //Donor header
        String requestNumber=requestPayload.getNumber(); //requested phone number

        //check if donor header is correct
        Boolean isValidDonor= operatorService.isExist(requestDonor);
        if (!isValidDonor){
            mp.put("message","Incorrect Donor Header");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        //check if the current operator of the number
        String currentOperator= phoneNumberService.getOperator(requestNumber); //get the current operator of the number
        if (currentOperator.equals(organization)){ //Current Operator is the same operator sends this request (request not valid)
            mp.put("message","Phone Number is already belongs to your operator");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        if (!currentOperator.equals(requestDonor)){ //Current Operator isn't the donor one
            mp.put("message","Phone Number is not belongs to donor operator, use the correct donor");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }

        //Check if it's already a pending request
        if (requestService.isPending(organization,requestPayload)){
            mp.put("message","Request is already pending now");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        //Finally if the request is correct and not pending .. add the request in database
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
        String donor = requestService.getDonor(requestID);
        if (donor==null){ // No request with this ID
            mp.put("message","No request with this ID , try the correct id");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        if (!donor.equals(organization)){ //check if the operator sends the request isn't the donor
            mp.put("message","Only the donor can accept or reject the porting request");
            return new ResponseEntity<>(mp, HttpStatus.UNAUTHORIZED);
        }
        if (requestService.accept(requestID)){
            String phoneNumber= requestService.getPhoneNumber(requestID);
            String recipient = requestService.getRecipient(requestID); // the new operator
            phoneNumberService.setOperator(phoneNumber,recipient);
            mp.put("message","Request Accepted Successfully");
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        else {
            mp.put("message","Error: This Request is not pending anymore");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/RejectRequest")
    public ResponseEntity<?> rejectRequest(@RequestHeader String organization,@RequestParam Long requestID){
        Map<String, String> mp = new HashMap<>();
        String donor = requestService.getDonor(requestID);
        if (donor==null){ // No request with this ID
            mp.put("message","No request with this ID , try the correct id");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        if (!donor.equals(organization)){ //check if the operator sends the request isn't the donor
            mp.put("message","Only the donor can accept or reject the porting request");
            return new ResponseEntity<>(mp, HttpStatus.UNAUTHORIZED);
        }
        if (requestService.reject(requestID)){
            mp.put("message","Request Rejected Successfully");
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        else {
            mp.put("message","Error: This Request is not pending anymore");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
    }

        @GetMapping("/Test")
    public ResponseEntity<?> test(){

        Map<String, String> mp = new HashMap<>();
        if(true){
            mp.put("message","User Email updated successfully");
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        mp.put("message","User password is wrong , try again");
        return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
    }

}

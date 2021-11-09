package com.example.demo.exceptions;

import org.springframework.stereotype.Component;

@Component
//Set of all the error messages passed by ath APIs
public class GlobalExceptions {
    public String errIncorrectDonorHeader = "Incorrect Donor Header";
    public String errPhoneNumberBelongToOperator = "Phone Number is already belongs to your operator";
    public String errPhoneNumberNotBelongToOperator= "Phone Number is not belongs to donor operator, use the correct donor";
    public String errIsPending="Request is already pending now";
    public String errNoRequestID="No request with this ID , try the correct id";
    public String errNotTheDonor="Only the donor can accept or reject the porting request";
    public String errNotPending = "This Request is not pending anymore";
    public String errWrongPhoneNumber ="Wrong Phone number";
    public String errNoRequestsAvailable = "No Requests Available";
}

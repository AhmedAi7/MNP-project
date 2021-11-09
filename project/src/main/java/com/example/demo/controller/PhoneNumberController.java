package com.example.demo.controller;

import com.example.demo.interfaces.IPhoneNumberService;
import com.example.demo.payload.PhoneNumberPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PhoneNumberController {

    @Autowired
    IPhoneNumberService phoneNumberService;
    @GetMapping("/PhoneNumberStatus")
    public ResponseEntity<?> getPhoneNumStatus(@RequestBody PhoneNumberPayload phoneNumberPayload) {
        Map<String, String> mp = new HashMap<>();
        String number=phoneNumberPayload.getNumber();
        //get the current operator of the phone number
        String holder = phoneNumberService.getOperator(number);
        //Check if the phone number is wrong (wrong prefix)
        if (holder==null){
            mp.put("message","Wrong Phone number");
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        //Check if the number has ported before (exists in Phone_Number Table)
        if (phoneNumberService.isExist(number)) {
            //phone number is ported
            mp.put("message", "This phone number has been ported ... The current mobile network holder is " + holder);
            return new ResponseEntity<>(mp, HttpStatus.OK);
        } else {
            //Phone number is not ported
            mp.put("message", "This phone number has not been ported before ... The current mobile network is " + holder);
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
    }
}

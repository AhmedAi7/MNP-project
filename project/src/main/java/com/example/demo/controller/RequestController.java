package com.example.demo.controller;

import com.example.demo.exceptions.GlobalExceptions;
import com.example.demo.interfaces.IRequestService;
import com.example.demo.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RequestController {
    @Autowired
    IRequestService requestService;
    @Autowired
    GlobalExceptions globalExceptions;

    @GetMapping("/Requests")
    public ResponseEntity<?> getAllRequests(@RequestHeader String organization) {
        Map<String, String> mp = new HashMap<>();
        // get all requests for the operator that sends the request
        List<Request> requests = requestService.getAllRequests(organization);
        if (requests!=null){ // There is one or more requests
            mp.put("message", "Requests: ");
            requests.forEach((request -> {
                mp.put("Request ID "+request.getId()+" : ",request.toString());
            }));
            return new ResponseEntity<>(mp, HttpStatus.OK);
        }
        else {
            mp.put("message", globalExceptions.errNoRequestsAvailable);
            return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
        }
        }
}

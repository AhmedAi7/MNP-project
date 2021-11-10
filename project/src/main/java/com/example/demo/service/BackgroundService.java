package com.example.demo.service;

import com.example.demo.interfaces.IRequestService;
import com.example.demo.model.Request;
import com.example.demo.payload.StatusEnum;
import com.example.demo.repository.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

//Service of Background threads methods (run independently every specific time)

@Configuration
@EnableScheduling
public class BackgroundService { // Background Methods
    @Autowired
    RequestRepo requestRepo;
    @Autowired
    IRequestService requestService;

    //Cancel Expired Requests
    @Scheduled(fixedDelay = 1000) // after the end of previous one by 1 sec
    public void cancelRequests() { //Cancel requests that exceed lifetime (2min)
        requestRepo.findAllByStatusAndExpiredTimeBefore //get list of all pending requests with expired time after now (expired requests)
                (StatusEnum.PENDING,new Date(System.currentTimeMillis())).forEach((request -> {
           requestService.cancel(request); // cancel all the expired requests
        }));
    }
}

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
import java.util.List;

@Configuration
@EnableScheduling
public class BackgroundService { // Background Methods
    @Autowired
    RequestRepo requestRepo;
    @Autowired
    IRequestService requestService;

    @Scheduled(fixedDelay = 1000) // after the end of previous one by 1 sec
    public void cancelRequests() { //Cancel requests that exceed lifetime (2min)
        requestRepo.findAllByStatusAndExpiredTimeBefore //get list of all pending requests with expired time after now (expired requests)
                (StatusEnum.PENDING,new Date(System.currentTimeMillis())).forEach((request -> {
           requestService.cancel(request); // cancel all the expired requests
        }));
    }
    /*
    @Scheduled(fixedDelay = 100000) // after the end of previous one by 1 sec
    public void testRequests(){
        requestRepo.findAllByDonorOrRecipientOrStatus("etisalat","etisalat",StatusEnum.ACCEPTED)
                .forEach((request -> {
                    System.out.println(request.toString());
                }));
    }
*/
}

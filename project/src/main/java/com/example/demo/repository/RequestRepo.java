package com.example.demo.repository;

import com.example.demo.model.Request;
import com.example.demo.payload.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestRepo extends JpaRepository<Request,Long> {
    //check if there is a request exist by Recipient And Donor And Number And Status
    boolean existsByRecipientAndDonorAndNumberAndStatus(String recipient, String donor, String number, StatusEnum status);
    //get list of all requests with Status : status variable and Date: any time Before date variable
    List<Request> findAllByStatusAndExpiredTimeBefore(StatusEnum status, Date date);

    List<Request> findAllByDonorOrRecipientOrStatus(String donor,String Recipient,StatusEnum status);
}

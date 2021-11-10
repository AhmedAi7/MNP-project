package com.example.demo.model;

import com.example.demo.payload.StatusEnum;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String recipient;
    private String donor;
    private String number;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "expired_time")
    private Date expiredTime;

    public Request(String recipient, String donor, String number) {
        this.recipient = recipient;
        this.donor = donor;
        this.number = number;
        this.status = StatusEnum.PENDING; //Default status is pending

        Calendar date = Calendar.getInstance(); //get current time
        long timeInSecs = date.getTimeInMillis();
        Date afterAdding2Mins = new Date(timeInSecs + (2 * 60 * 1000)); //set expired time after 2 min from now
        this.expiredTime = afterAdding2Mins;
    }
    public Request() {
    }
    public Request(Request request){
        this.id = request.id;
        this.recipient = request.recipient;
        this.donor = request.donor;
        this.number = request.number;
        this.status = request.status;
        this.expiredTime = request.expiredTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", donor='" + donor + '\'' +
                ", number='" + number + '\'' +
                ", status=" + status +
                ", expiredTime=" + expiredTime +
                '}';
    }
}

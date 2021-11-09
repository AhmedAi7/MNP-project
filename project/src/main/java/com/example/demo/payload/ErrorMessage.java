package com.example.demo.payload;

import java.util.Date;
import java.util.Map;

//Object to be returned for any error
public class ErrorMessage {
    private Date timestamp;
    private Map<String, String> message;

    public ErrorMessage(Date timestamp, Map<String, String> message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//Object of Phone number that has to be sent as a request body
public class PhoneNumberPayload {
    @NotBlank
    @Pattern(regexp = "01[0-9]{9}",message = "Wrong phone number") //must match phone number regex
    private String number;

    public PhoneNumberPayload(@JsonProperty("number") String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

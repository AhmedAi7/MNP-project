package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RequestPayload {
// The recipient is the operator that send request "From the organization header";
    @NotBlank(message = "Donor header is mandatory")
    private String donor;
    @NotBlank
    @Pattern(regexp = "01[0-9]{9}",message = "Wrong phone number") //must match phone number regex
    private String number;

    public RequestPayload(String donor, String number) {
        this.donor = donor;
        this.number = number;
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
}

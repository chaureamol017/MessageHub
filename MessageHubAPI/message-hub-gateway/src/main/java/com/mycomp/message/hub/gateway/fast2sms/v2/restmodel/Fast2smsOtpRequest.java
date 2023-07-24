package com.mycomp.message.hub.gateway.fast2sms.v2.restmodel;

import java.util.List;

public class Fast2smsOtpRequest {
    private String sendId;
    private String message;
    private List<String> numbers;


    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}

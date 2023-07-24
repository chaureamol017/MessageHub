package com.mycomp.message.hub.gateway.fast2sms.v2.restmodel;

public enum Route {
    OTP("otp"),
    DLT("dlt"),
    QUICK("q");

    private final String value;

    Route(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

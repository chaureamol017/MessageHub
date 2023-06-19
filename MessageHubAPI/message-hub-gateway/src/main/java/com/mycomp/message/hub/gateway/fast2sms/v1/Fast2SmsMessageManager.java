package com.mycomp.message.hub.gateway.fast2sms.v1;

import com.mycomp.message.hub.api.sender.OtpSender;
import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.request.TextMessageRequest;
import com.mycomp.message.hub.response.TextMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Fast2SmsMessageManager {
    @Autowired private RestMethodFactory restMethodFactory;
    @Autowired
    @Qualifier("fast2SmsOtpSenderV1")
    private OtpSender<Fast2smsRestApiClient> otpSender;

    public TextMessageResponse sendOtp(Credentials credentials, TextMessageRequest request) {
        Fast2smsRestApiClient apiClient = new Fast2smsRestApiClient(restMethodFactory, credentials);

        return otpSender.sendOtp(apiClient, request);
    }
}

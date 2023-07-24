package com.mycomp.message.hub.gateway.fast2sms.v1;

import com.mycomp.message.hub.api.MessageManager;
import com.mycomp.message.hub.api.sender.OtpSender;
import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.api.response.TextMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Fast2SmsMessageManager implements MessageManager {
    @Autowired private RestMethodFactory restMethodFactory;
    @Autowired
    @Qualifier("fast2SmsOtpSenderV1")
    private OtpSender<Fast2smsRestApiClient> otpSender;

    @Override
    public TextMessageResponse sendOtpMessage(Credentials credentials, TextMessageRequest request) {
        final Fast2smsRestApiClient apiClient = new Fast2smsRestApiClient(restMethodFactory, credentials);

        return otpSender.send(apiClient, request);
    }

    @Override
    public TextMessageResponse sendDltMessage(Credentials credentials, TextMessageRequest request) {
        throw new UnsupportedOperationException("Method Not Supported");
    }

    @Override
    public TextMessageResponse sendQuickMessage(Credentials credentials, TextMessageRequest request) {
        throw new UnsupportedOperationException("Method Not Supported");
    }
}

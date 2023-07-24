package com.mycomp.message.hub.gateway.fast2sms.v2;

import com.mycomp.message.hub.api.MessageManager;
import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.api.response.TextMessageResponse;
import com.mycomp.message.hub.core.restclient.model.Credentials;

public class Fast2SmsMessageManager implements MessageManager {

    @Override
    public TextMessageResponse sendOtpMessage(Credentials credentials, TextMessageRequest request) {
        return null;
    }

    @Override
    public TextMessageResponse sendDltMessage(Credentials credentials, TextMessageRequest request) {
        return null;
    }

    @Override
    public TextMessageResponse sendQuickMessage(Credentials credentials, TextMessageRequest request) {
        throw new UnsupportedOperationException("Method not supported");
    }
}

package com.mycomp.message.hub.api;

import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.api.response.TextMessageResponse;
import com.mycomp.message.hub.core.restclient.model.Credentials;

public interface MessageManager {
    TextMessageResponse sendOtpMessage(Credentials credentials, TextMessageRequest request);
    TextMessageResponse sendDltMessage(Credentials credentials, TextMessageRequest request);
    TextMessageResponse sendQuickMessage(Credentials credentials, TextMessageRequest request);
}

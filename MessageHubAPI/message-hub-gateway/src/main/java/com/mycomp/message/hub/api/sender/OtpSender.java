package com.mycomp.message.hub.api.sender;

import com.mycomp.message.hub.api.RestApiClient;
import com.mycomp.message.hub.request.TextMessageRequest;
import com.mycomp.message.hub.response.TextMessageResponse;

public abstract class OtpSender<C extends RestApiClient> {

    public abstract TextMessageResponse sendOtp(C apiClient, TextMessageRequest request);

}

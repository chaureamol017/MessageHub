package com.mycomp.message.hub.api.sender;

import com.mycomp.message.hub.api.RestApiClient;
import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.api.response.TextMessageResponse;

public abstract class PromotionalMessageSender<C extends RestApiClient> {

    protected abstract TextMessageResponse send(C apiClient, TextMessageRequest request);
}

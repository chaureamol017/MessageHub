package com.mycomp.message.hub.api.sender;

import com.mycomp.message.hub.request.TextMessageRequest;
import com.mycomp.message.hub.response.TextMessageResponse;

public abstract class QuickSmsSender {

    abstract TextMessageResponse sendQuickMessage(TextMessageRequest request);
}

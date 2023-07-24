package com.mycomp.message.hub.gateway.fast2sms.v2.sender;

import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.api.response.TextMessageResponse;
import com.mycomp.message.hub.api.sender.TransactionalSmsSender;
import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.gateway.fast2sms.v2.Fast2smsRestApiClient;
import com.mycomp.message.hub.gateway.fast2sms.v2.adapter.Fast2smsOtpResponseToTextMessageResponseAdapter;
import com.mycomp.message.hub.gateway.fast2sms.v2.restmodel.Fast2smsOtpResponse;
import org.springframework.stereotype.Component;

@Component("fast2SmsTransactionalSmsSenderV2")
public class Fast2SmsTransactionalSmsSender extends TransactionalSmsSender<Fast2smsRestApiClient> {

    @Override
    public TextMessageResponse send(final Fast2smsRestApiClient apiClient, final TextMessageRequest request) {
        final RestMethod<Fast2smsOtpResponse> restMethod = apiClient.createTransactionalMethod(request);

        final Fast2smsOtpResponse response = restMethod.call();
        final TextMessageResponse textMessageResponse = Fast2smsOtpResponseToTextMessageResponseAdapter.INSTANCE.apply(response);

        return textMessageResponse;
    }
}

package com.mycomp.message.hub.gateway.fast2sms.v1.sender;

import com.mycomp.message.hub.api.sender.OtpSender;
import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.gateway.fast2sms.v1.Fast2smsRestApiClient;
import com.mycomp.message.hub.gateway.fast2sms.v1.adapter.Fast2smsOtpResponseToTextMessageResponseAdapter;
import com.mycomp.message.hub.gateway.fast2sms.v1.adapter.Fast2smsRequestParamsAdapter;
import com.mycomp.message.hub.gateway.fast2sms.v1.restmodel.Fast2smsOtpResponse;
import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.api.response.TextMessageResponse;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("fast2SmsOtpSenderV1")
public class Fast2SmsOtpSender extends OtpSender<Fast2smsRestApiClient> {

    @Override
    public TextMessageResponse send(final Fast2smsRestApiClient apiClient, final TextMessageRequest request) {
        final List<Pair<String, String>> params = Fast2smsRequestParamsAdapter.INSTANCE.apply(request);
        final RestMethod<Fast2smsOtpResponse> restMethodForOtp = apiClient.createOtpMethod(params);

        final Fast2smsOtpResponse response = restMethodForOtp.call();
        final TextMessageResponse textMessageResponse = Fast2smsOtpResponseToTextMessageResponseAdapter.INSTANCE.apply(response);

        return textMessageResponse;
    }

}

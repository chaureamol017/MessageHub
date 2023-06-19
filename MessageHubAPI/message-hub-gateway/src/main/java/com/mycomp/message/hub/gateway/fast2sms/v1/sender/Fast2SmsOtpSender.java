package com.mycomp.message.hub.gateway.fast2sms.v1.sender;

import com.mycomp.message.hub.api.sender.OtpSender;
import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.gateway.fast2sms.v1.Fast2smsRestApiClient;
import com.mycomp.message.hub.gateway.fast2sms.v1.adapter.Fast2smsOtpResponseToTextMessageResponseAdapter;
import com.mycomp.message.hub.gateway.fast2sms.v1.adapter.Fast2smsRequestParamsAdapter;
import com.mycomp.message.hub.gateway.fast2sms.v1.restmodel.Fast2smsOtpResponse;
import com.mycomp.message.hub.request.TextMessageRequest;
import com.mycomp.message.hub.response.TextMessageResponse;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Component("fast2SmsOtpSenderV1")
public class Fast2SmsOtpSender extends OtpSender<Fast2smsRestApiClient> {

    @Override
    public TextMessageResponse sendOtp(final Fast2smsRestApiClient apiClient, final TextMessageRequest request) {
        final List<Pair<String, String>> params = Fast2smsRequestParamsAdapter.INSTANCE.apply(request);
        final RestMethod<Fast2smsOtpResponse> restMethodForOtp = apiClient.createOtpMethod(null, params);

        final Fast2smsOtpResponse response = restMethodForOtp.call();
        final TextMessageResponse textMessageResponse = Fast2smsOtpResponseToTextMessageResponseAdapter.INSTANCE.apply(response);

        return textMessageResponse;
    }

}

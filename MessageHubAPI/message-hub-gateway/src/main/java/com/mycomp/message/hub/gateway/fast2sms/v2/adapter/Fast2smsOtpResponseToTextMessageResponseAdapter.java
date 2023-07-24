package com.mycomp.message.hub.gateway.fast2sms.v2.adapter;

import com.mycomp.message.hub.api.response.TextMessageResponse;
import com.mycomp.message.hub.gateway.fast2sms.v2.restmodel.Fast2smsOtpResponse;

import java.util.function.Function;

public class Fast2smsOtpResponseToTextMessageResponseAdapter implements Function<Fast2smsOtpResponse, TextMessageResponse> {
    public static final Fast2smsOtpResponseToTextMessageResponseAdapter INSTANCE = new Fast2smsOtpResponseToTextMessageResponseAdapter();

    private Fast2smsOtpResponseToTextMessageResponseAdapter() {
    }

    @Override
    public TextMessageResponse apply(final Fast2smsOtpResponse apiResponse) {
        if (apiResponse.isSuccess()) {
            return TextMessageResponse.createSuccess(apiResponse.getRequestId());
        }

        return TextMessageResponse.createFail(apiResponse.getRequestId(), apiResponse.getMessage());
    }
}

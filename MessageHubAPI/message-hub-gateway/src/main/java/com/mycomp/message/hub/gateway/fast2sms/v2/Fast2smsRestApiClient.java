package com.mycomp.message.hub.gateway.fast2sms.v2;

import com.google.common.collect.Lists;
import com.mycomp.message.hub.api.RestApiClient;
import com.mycomp.message.hub.api.request.TextMessageRequest;
import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.gateway.fast2sms.v2.restmodel.Fast2smsOtpResponse;
import com.mycomp.message.hub.gateway.fast2sms.v2.restmodel.Route;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

public class Fast2smsRestApiClient extends RestApiClient<TextMessageRequest, Fast2smsOtpResponse> {
    private static final String BASE_URL = "https://www.fast2sms.com/dev/bulk";
    private static final String API_VERSION = "V2";
    private RestMethodFactory restMethodFactory;


    public Fast2smsRestApiClient(final RestMethodFactory restMethodFactory, final Credentials credentials) {
        super(credentials, BASE_URL, API_VERSION);
        this.restMethodFactory = restMethodFactory;
    }

    @Override
    public String getGatewayName() {
        return "Fast2sms";
    }

    @Override
    public RestMethodFactory getRestMethodFactory() {
        return restMethodFactory;
    }

    @Override
    public String getVersionUrl() {
        return API_VERSION;
    }

    public RestMethod<Fast2smsOtpResponse> createOtpMethod(final TextMessageRequest request) {
        return createFast2smsPostRestMethod(request, Route.OTP);
    }

    public RestMethod<Fast2smsOtpResponse> createPromotionalMethod(final TextMessageRequest request) {
        return createFast2smsGetRestMethod(request, Route.DLT);
    }

    public RestMethod<Fast2smsOtpResponse> createTransactionalMethod(final TextMessageRequest request) {
        return createFast2smsPostRestMethod(request, Route.DLT);
    }

    private RestMethod<Fast2smsOtpResponse> createFast2smsGetRestMethod(TextMessageRequest request, Route route) {
        final String endpoint = getServiceEndpoint("");
        final RestMethod<Fast2smsOtpResponse> restMethodForGet = createRestMethodForGet(endpoint, true);
        addParametersForGet(request, restMethodForGet, route);

        return restMethodForGet;
    }

    private void addParametersForGet(final TextMessageRequest request, RestMethod<Fast2smsOtpResponse> restMethod, final Route route) {
        try {
            String language = request.getLanguage() == null || request.getLanguage().trim().length() ==0 ? "" : "english";

            restMethod.addParam("route", route.getValue());
            restMethod.addParam("sender_id", request.getSendId());
            restMethod.addParam("numbers", request.commaSeparatedNumbers());

            switch (route) {
                case OTP:
                    restMethod.addParam("variables_values", request.pipeSeparatedVariables());
                    break;
                case DLT:
                    restMethod.addParam("message", request.getMessageId());
                    break;
                case QUICK:
                    final String message = URLEncoder.encode(request.getMessage(), "UTF-8");
                    restMethod.addParam("message", message);
                    restMethod.addParam("language" , language);
                break;
            }
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private RestMethod<Fast2smsOtpResponse> createFast2smsPostRestMethod(TextMessageRequest request, Route route) {
        final String endpoint = getServiceEndpoint("");
        final RestMethod<Fast2smsOtpResponse> restMethodForGet = createRestMethodForPost(endpoint, true);

        addParametersForPost(request, restMethodForGet, route);

        return restMethodForGet;
    }

    private void addParametersForPost(final TextMessageRequest request, RestMethod<Fast2smsOtpResponse> restMethod, final Route route) {
        try {
            final String sendId = request.getSendId();
            final String numbers = request.getNumbers().stream().collect(Collectors.joining(","));
            final List<String> params = Lists.newArrayList();

            params.add("route=" + route.getValue());
            params.add("sender_id=" + sendId);
            params.add("numbers=" + numbers);
            switch (route) {
                case OTP:
                    params.add("variables_values=" + request.pipeSeparatedVariables());
                break;
                case DLT:
                    params.add("message=" + request.getMessageId());
                break;
                case QUICK:
                    final String message = URLEncoder.encode(request.getMessage(), "UTF-8");
                    params.add("message=" + message);
                break;
            }

            final String requestBody = params.stream().collect(Collectors.joining("&"));
            restMethod.setBody(requestBody);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

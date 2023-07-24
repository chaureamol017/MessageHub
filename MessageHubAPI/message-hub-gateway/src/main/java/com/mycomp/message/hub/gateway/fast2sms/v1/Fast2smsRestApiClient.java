package com.mycomp.message.hub.gateway.fast2sms.v1;

import com.mycomp.message.hub.api.RestApiClient;
import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.gateway.fast2sms.v1.restmodel.Fast2smsOtpRequest;
import com.mycomp.message.hub.gateway.fast2sms.v1.restmodel.Fast2smsOtpResponse;
import javafx.util.Pair;

import java.util.List;

public class Fast2smsRestApiClient extends RestApiClient<List<Pair<String, String>>, Fast2smsOtpResponse> {
    private static final String BASE_URL = "https://www.fast2sms.com/dev/bulk";
    private static final String API_VERSION = null;
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
        return "";
    }

    public RestMethod<Fast2smsOtpResponse> createOtpMethod(final List<Pair<String, String>> params) {
        return createFast2smsGetRestMethod(params, "o");
    }

    public RestMethod<Fast2smsOtpResponse> createPromotionalMethod(final List<Pair<String, String>> params) {
        return createFast2smsGetRestMethod(params, "p");
    }

    public RestMethod<Fast2smsOtpResponse> createTransactionalMethod(final List<Pair<String, String>> params) {
        return createFast2smsGetRestMethod(params, "t");
    }

    private RestMethod<Fast2smsOtpResponse> createFast2smsGetRestMethod(List<Pair<String, String>> params, String route) {
        final String endpoint = getServiceEndpoint("");

        final RestMethod<Fast2smsOtpResponse> restMethodForGet = createRestMethodForGet(endpoint, true);
//        restMethodForGet.addHeader("cache-control", "no-cache");

        addParameters(params, restMethodForGet, route);

        return restMethodForGet;
    }

    private void addParameters(List<Pair<String, String>> params, RestMethod<Fast2smsOtpResponse> methodForPost, String route) {
        String language = "english";
//        methodForPost.addParam("authorization", credentials.getApiKey());
        methodForPost.addParam("language" , language);
        methodForPost.addParam("route", route);
        params.forEach(pair -> {
            methodForPost.addParam(pair.getKey(), pair.getValue());
        });
    }
}

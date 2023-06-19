package com.mycomp.message.hub.api;

import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RestApiClient {

    private final String baseUrl;
    private final String apiVersion;

    protected RestApiClient(final String baseUrl, final String apiVersion) {
        this.baseUrl = baseUrl;
        this.apiVersion = apiVersion;
    }

    public final String getBaseUrl() {
        return baseUrl;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public final String getServiceEndpoint(final String serviceRelativeUrl) {
        String serviceEndpoint = getBaseUrl();

        String versionEndPoint = getApiVersion() != null ? getApiVersion() : "";
        if (versionEndPoint.length() > 0 && isSeparateVersionInUrl()) {
            versionEndPoint = "/" + versionEndPoint;
        }

        final String relativeUrl = serviceRelativeUrl != null && serviceRelativeUrl.length() > 0 ? "/" + serviceRelativeUrl : "";

        return serviceEndpoint + versionEndPoint + relativeUrl;
    }

    public abstract String getGatewayName();
    public abstract RestMethodFactory getRestMethodFactory();
    public abstract Boolean isSeparateVersionInUrl();


    
    protected <R> RestMethod<R> createRestMethodForGet(final String endpointUrl, final Class<R> responseType) {
        final RestMethod<R> restMethod = getRestMethodFactory().forGet(endpointUrl, responseType);

        return restMethod;
    }

    
    protected <R> RestMethod<R> createRestMethodForPost(final String endpointUrl, final Class<R> responseType) {
        final RestMethod<R> restMethod = getRestMethodFactory().forPost(endpointUrl, responseType);

        return restMethod;
    }

    
    protected <R> RestMethod<R> createRestMethodForPut(final String endpointUrl, final Class<R> responseType) {
        final RestMethod<R> restMethod = getRestMethodFactory().forPut(endpointUrl, responseType);

        return restMethod;
    }

    
    protected <R> RestMethod<R> createRestMethodForDelete(final String endpointUrl, final Class<R> responseType) {
        final RestMethod<R> restMethod = getRestMethodFactory().forDelete(endpointUrl, responseType);

        return restMethod;
    }

}

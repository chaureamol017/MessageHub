package com.mycomp.message.hub.api;

import com.mycomp.message.hub.core.restclient.RestMethod;
import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.core.restclient.spring.SpringRestMethod;
import javafx.util.Pair;
import org.springframework.http.HttpMethod;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class RestApiClient<I, R> {
    private final String baseUrl;
    private final String apiVersion;
    private final Credentials credentials;

    protected RestApiClient(Credentials credentials, final String baseUrl, final String apiVersion) {
        this.credentials = credentials;
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
        final String serviceEndpoint = getBaseUrl();
        final String versionEndPoint = getVersionUrl() != null ? getVersionUrl() : "";
        final String relativeUrl = serviceRelativeUrl != null && serviceRelativeUrl.length() > 0 ? "/" + serviceRelativeUrl : "";

        return serviceEndpoint + versionEndPoint + relativeUrl;
    }

    public abstract String getGatewayName();
    public abstract RestMethodFactory getRestMethodFactory();
    public abstract String getVersionUrl();
    public abstract RestMethod<R> createOtpMethod(final I request);
    public abstract RestMethod<R> createPromotionalMethod(final I request);
    public abstract RestMethod<R> createTransactionalMethod(final I request);

    protected  RestMethod<R> createRestMethodForGet(final String endpointUrl, final Boolean canAddDefaultParameters) {
        final Class<R> responseType = getParametrizeClass(1);
        final RestMethod<R> restMethod = getRestMethodFactory().forGet(endpointUrl, responseType);

        addDefaultParameters(restMethod, canAddDefaultParameters);

        return restMethod;
    }


    protected RestMethod<R> createRestMethodForPost(final String endpointUrl, final Boolean canAddDefaultParameters) {
        final Class<R> responseType = getParametrizeClass(1);
        final RestMethod<R> restMethod = getRestMethodFactory().forPost(endpointUrl, responseType);

        addDefaultParameters(restMethod, canAddDefaultParameters);

        return restMethod;
    }

    
    protected RestMethod<R> createRestMethodForPut(final String endpointUrl, final Boolean canAddDefaultParameters) {
        final Class<R> responseType = getParametrizeClass(1);
        final RestMethod<R> restMethod = getRestMethodFactory().forPut(endpointUrl, responseType);

        addDefaultParameters(restMethod, canAddDefaultParameters);

        return restMethod;
    }

    
    protected RestMethod<R> createRestMethodForDelete(final String endpointUrl, final Boolean canAddDefaultParameters) {
        final Class<R> responseType = getParametrizeClass(1);
        final RestMethod<R> restMethod = getRestMethodFactory().forDelete(endpointUrl, responseType);

        addDefaultParameters(restMethod, canAddDefaultParameters);

        return restMethod;
    }

    private Class<R> getParametrizeClass(final int index) {
        final Class<R> parametrizeClass = (Class<R>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[index];
        return parametrizeClass;
    }

    private void addDefaultParameters(final RestMethod<R> restMethod, Boolean canAddDefaultParameters) {
        if (!canAddDefaultParameters) {
            return;
        }

        if (((SpringRestMethod) restMethod).getHttpMethod() == HttpMethod.GET) {
            restMethod.addHeader("cache-control", "no-cache");
        } else {
            restMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }
        restMethod.addParam("authorization", credentials.getApiKey());

    }

}

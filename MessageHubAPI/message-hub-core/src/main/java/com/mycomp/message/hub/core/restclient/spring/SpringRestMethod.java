package com.mycomp.message.hub.core.restclient.spring;

import com.mycomp.message.hub.core.restclient.RestMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringRestMethod<T> implements RestMethod<T> {
    //    private static final Logger LOG = Logger.getLog(SpringRestMethod.class);
    private final RestTemplate restTemplate;
    private final HttpMethod httpMethod;
    private final String url;
    private final Class<T> responseType;
    private final Map<String, String> headers;
    private final Map<String, Object> parameters;
    private Object requestBody;
    private UriComponentsBuilder uriComponentsBuilder;

    public SpringRestMethod(final RestTemplate restTemplate, final HttpMethod httpMethod, final String url, final Class<T> responseType) {
        this.restTemplate = restTemplate;
        this.httpMethod = httpMethod;
        this.url = url;
        this.responseType = responseType;
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        this.headers = new HashMap();
        this.parameters = new HashMap();
//        this.headers = Maps.newHashMap();
//        this.parameters = Maps.newHashMap();
    }

    public SpringRestMethod<T> addHeader(final String name, final String value) {
        this.headers.put(name, value);
        return this;
    }

    public SpringRestMethod<T> addParam(final String name, final Object value) {
        this.parameters.put(name, value);
        return this;
    }


    public RestMethod<T> setBody(final Object body) {
        this.requestBody = body;
        return this;
    }

    public T call() {
        HttpHeaders httpHeaders = this.buildHttpHeaders();
        HttpEntity<?> httpEntity = this.instantiateHttpEntity(httpHeaders);
        URI uri = this.uriComponentsBuilder.buildAndExpand(this.parameters).toUri();

        try {
            ResponseEntity<T> response = this.getRestTemplate().exchange(uri, this.httpMethod, httpEntity, this.responseType);
            String respHeaders = response.getHeaders() == null ? "" : (String) response.getHeaders().entrySet().stream().map((e) -> {
                return "KEY: " + (String) e.getKey() + " VALUE: " + (String) ((List) e.getValue()).stream().collect(Collectors.joining(","));
            }).collect(Collectors.joining("## "));
//            LOG.info("Success REST METHOD={0} \nSTATUS_CODE={1} \nRESPONSE_HEADERS={2} \nURI={3} ", new Object[]{this.httpMethod, response.getStatusCode(), respHeaders, uri.toString()});
            return response.getBody();
        } catch (HttpStatusCodeException var6) {
//            LOG.warn("Failed REST method={0} url={1} responseCode={2}, responseBody={3}", var6, new Object[]{this.httpMethod, uri, var6.getStatusCode(), var6.getResponseBodyAsString()});
            throw var6;
        }
    }


    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }


    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }


    public Class<T> getResponseType() {
        return this.responseType;
    }


    public String getUrl() {
        return this.url;
    }


    private HttpHeaders buildHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Iterator var2 = this.headers.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var2.next();
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            httpHeaders.set(name, value);
        }

        this.setContentType(httpHeaders);
        return httpHeaders;
    }

    private void setContentType(final HttpHeaders httpHeaders) {
        if (this.requestBody != null) {
            String contentType = this.headers.get("Content-Type");
            if (contentType == null) {
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            } else {
                httpHeaders.setContentType(MediaType.parseMediaType(contentType));
            }
        }

    }


    private HttpEntity<?> instantiateHttpEntity(final HttpHeaders httpHeaders) {
        return this.requestBody != null ? new HttpEntity(this.requestBody, httpHeaders) : new HttpEntity(httpHeaders);
    }


    public String getHeader(final String headerName) {
        return (String) this.headers.get(headerName);
    }
}

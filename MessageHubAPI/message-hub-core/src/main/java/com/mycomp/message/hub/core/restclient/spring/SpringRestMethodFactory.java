package com.mycomp.message.hub.core.restclient.spring;

import com.mycomp.message.hub.core.restclient.RestMethodFactory;
import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.core.utils.Utility;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
public class SpringRestMethodFactory implements RestMethodFactory {
    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0";

//    @Qualifier("springRestTemplateWithDeleteRequestBody")
    private RestTemplate restTemplate;
    @Value("rest.useragent") private String userAgent;

    @Autowired
    public SpringRestMethodFactory(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <R> SpringRestMethod<R> forGet(final String url, final Class<R> responseType) {
        return this.forAction(url, responseType, HttpMethod.GET);
    }

    public <R> SpringRestMethod<R> forPost(final String url, final Class<R> responseType) {
        return this.forAction(url, responseType, HttpMethod.POST);
    }

    public <R> SpringRestMethod<R> forPut(final String url, final Class<R> responseType) {
        return this.forAction(url, responseType, HttpMethod.PUT);
    }

    public <R> SpringRestMethod<R> forDelete(final String url, final Class<R> responseType) {
        return this.forAction(url, responseType, HttpMethod.DELETE);
    }

    private <R> SpringRestMethod<R> forAction(final String url, final Class<R> responseType, final HttpMethod httpMethod) {
        final SpringRestMethod<R> method = new SpringRestMethod(this.restTemplate, httpMethod, url, responseType);
        final String userAgentHeader = Utility.firstNonEmpty(new CharSequence[]{userAgent, DEFAULT_USER_AGENT});

        method.addHeader("User-Agent", userAgentHeader);
        return method;
    }

    public <R> SpringRestMethod<R> forGet(final String url, final Class<R> responseType, final Credentials credential) {
        return this.forAction(url, responseType, credential, HttpMethod.GET);
    }

    public <R> SpringRestMethod<R> forPost(final String url, final Class<R> responseType, final Credentials credential) {
        return this.forAction(url, responseType, credential, HttpMethod.POST);
    }

    public <R> SpringRestMethod<R> forDelete(final String url, final Class<R> responseType, final Credentials credential) {
        return this.forAction(url, responseType, credential, HttpMethod.DELETE);
    }

    private <R> SpringRestMethod<R> forAction(final String url, final Class<R> responseType, final Credentials credential, final HttpMethod httpMethod) {
        SpringRestMethod<R> method = this.forAction(url, responseType, httpMethod);

        method.addHeader("Authorization", "Basic " + this.toBase64(credential));

        return method;
    }

    private String toBase64(final Credentials credential) {
        final Charset charset = Charset.forName("ISO-8859-1");
        final String plainCredentials = credential.getUsername() + ":" + credential.getPassword();

        return new String(Base64.encodeBase64(plainCredentials.getBytes(charset)), charset);
    }
}

package com.mycomp.message.hub.core.restclinet.spring;

import com.mycomp.message.hub.core.restclient.model.Credentials;
import com.mycomp.message.hub.core.restclient.spring.SpringRestMethod;
import com.mycomp.message.hub.core.restclient.spring.SpringRestMethodFactory;
import com.mycomp.message.hub.core.utils.Utility;
import junit.framework.Assert;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class SpringRestMethodFactoryTest {
    @Mock
    private RestTemplate mockTemplate;

    private SpringRestMethodFactory factory;

    private final String anyUrl = "http://abc.def/" + RandomStringUtils.randomAlphabetic(5);
    private final Credentials credential = Credentials.basicCredentials("123", "456");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        factory = new SpringRestMethodFactory(mockTemplate);

//        factory = BeanBuilder.of(SpringRestMethodFactory.class)
//                .with("springRestTemplate", mockTemplate)
//                .build();

    }

    @Test
    public void forGetShouldInstantiateSpringRestTemplateAndPassInRestTemplate() {
        final SpringRestMethod<String> method = factory.forGet(anyUrl, String.class);

        assertRestMethod(method, HttpMethod.GET, "forGet()");
    }

    @Test
    public void forPostShouldInstantiateSpringRestTemplateAndPassInRestTemplate() {
        final SpringRestMethod<String> method = factory.forPost(anyUrl, String.class);

        assertRestMethod(method, HttpMethod.POST, "forPost()");
    }


    @Test
    public void forPutShouldInstantiateSpringRestTemplateAndPassInRestTemplate() {
        final SpringRestMethod<String> method = factory.forPut(anyUrl, String.class);

        assertRestMethod(method, HttpMethod.PUT, "forPut()");
    }

    @Test
    public void forDeleteShouldInstantiateSpringRestTemplateAndPassInRestTemplate() {
        final SpringRestMethod<String> method = factory.forDelete(anyUrl, String.class);

        assertRestMethod(method, HttpMethod.DELETE, "forDelete()");
    }


    @Test
    public void shouldSetDefaultUserAgentFromConfigurationSinceAdmissionRequiresIt() {
        final SpringRestMethod<String> method = factory.forGet(anyUrl, String.class);
        Assert.assertEquals("User agent should have come from configuration", "Mozilla/5.0", method.getHeader("User-Agent"));
    }


    @Test
    public void shouldSetNonEmptyUserAgentIfConfiguredValueIsEmptySinceAdmissionRequiresIt() {
        final SpringRestMethod<String> method = factory.forGet(anyUrl, String.class);
        Assert.assertTrue("User agent should not be empty", Utility.notEmpty(method.getHeader("User-Agent")));
    }


    @Test
    public void shouldSetNonEmptyUserAgentIfConfiguredValueIsNullSinceAdmissionRequiresIt() {
        final SpringRestMethod<String> method = factory.forGet(anyUrl, String.class);
        Assert.assertTrue("User agent should not be empty", Utility.notEmpty(method.getHeader("User-Agent")));
    }

    @Test
    public void shouldSetBasicAuthenWithEncoded64UsernameAndPasswordForGET() {
        assertCredentialsHeader(factory.forGet(anyUrl, String.class, credential));
    }

    @Test
    public void shouldSetBasicAuthenWithEncoded64UsernameAndPasswordforPOST() {
        assertCredentialsHeader(factory.forPost(anyUrl, String.class, credential));
    }

    @Test
    public void shouldSetBasicAuthenWithEncoded64UsernameAndPasswordforDELETE() {
        assertCredentialsHeader(factory.forDelete(anyUrl, String.class, credential));
    }

    private void assertCredentialsHeader(final SpringRestMethod<String> method) {
        Assert.assertEquals("Username and Password should be based-64 encoded", "Basic " + toBased64("123:456"), method.getHeader("Authorization"));
    }

    private String toBased64(final String s) {
        final Charset charset = Charset.forName("ISO-8859-1");
        return new String(Base64.encodeBase64(s.getBytes(charset)), charset);
    }

    private void assertRestMethod(final SpringRestMethod<String> method, final HttpMethod httpMethod, final String methodName) {
        Assert.assertNotNull(methodName + " should never return null", method);
        Assert.assertEquals(methodName + " should create a RestMethod using " + httpMethod.name(), httpMethod, method.getHttpMethod());
        Assert.assertSame("Should pass Spring-managed RestTemplate instance to new method", mockTemplate, method.getRestTemplate());
        Assert.assertSame("Should pass correct response type to RestMethod", String.class, method.getResponseType());
        Assert.assertEquals("Should pass URL to RestMethod", anyUrl, method.getUrl());
    }

}

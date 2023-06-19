package com.mycomp.message.hub.core.restclinet.spring;

import com.mycomp.message.hub.core.fixture.EnumFixture;
import com.mycomp.message.hub.core.restclient.spring.SpringRestMethod;
import com.mycomp.message.hub.core.utils.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import static org.mockito.Mockito.verify;

public class SpringRestMethodTest {
    @Mock private RestTemplate mockTemplate;
    @Mock private ResponseEntity<String> mockResponse;

    @Captor private ArgumentCaptor<HttpEntity<?>> argHttpEntity;
    @Captor private ArgumentCaptor<URI> uriCaptor;

    private SpringRestMethod<String> method;

    private final HttpMethod anyHttpMethod = EnumFixture.random(HttpMethod.GET, HttpMethod.POST);
    private final String anyHeaderName = RandomStringUtils.randomAlphabetic(10);
    private final String anyHeaderValue = RandomStringUtils.randomAlphabetic(10);
    private final String anyParamName = RandomStringUtils.randomAlphabetic(10);
    private final Object anyParamValue = RandomUtils.nextLong();
    private final String anyUrl = "http://cobalt.com/rest/" + RandomStringUtils.randomAlphabetic(5);
    private final String anyRestResult = RandomStringUtils.randomAlphabetic(10);
    private final String anyBody = RandomStringUtils.randomAlphabetic(10);


    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        method = new SpringRestMethod<String>(mockTemplate, anyHttpMethod, anyUrl, String.class);

        Mockito.when(mockTemplate.exchange(
                Matchers.any(URI.class),
                Matchers.eq(anyHttpMethod),
                Matchers.any(HttpEntity.class),
                Matchers.eq(String.class))).thenReturn(mockResponse);

        Mockito.when(mockResponse.getBody()).thenReturn(anyRestResult);
    }


    @Test
    public void shouldRetainObjectsPassedToConstructor() {
        Assert.assertSame("RestTemplate", mockTemplate, method.getRestTemplate());
        Assert.assertEquals("HttpMethod", anyHttpMethod, method.getHttpMethod());
        Assert.assertEquals("URL", anyUrl, method.getUrl());
        Assert.assertEquals("Response type", String.class, method.getResponseType());
    }


    @Test
    public void shouldReturnResponseFromResponseEntity() {
        final String response = method.call();
        Assert.assertEquals("Did not return response from ResponseEntity", anyRestResult, response);
    }


    @Test
    public void shouldExecuteCallWithCorrectHeaders() {
        method.addHeader(anyHeaderName, anyHeaderValue);
        method.call();

        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyHeader(anyHeaderName, anyHeaderValue);
    }


    @Test
    public void addHeaderShouldReplaceHeaderWithTheSameName() {
        // Set an original value
        method.addHeader(anyHeaderName, anyHeaderValue);
        Assert.assertEquals("Original header value", anyHeaderValue, method.getHeader(anyHeaderName));

        // Replace it with a new value
        final String newValue = anyHeaderValue + RandomStringUtils.randomAlphabetic(5);
        method.addHeader(anyHeaderName, newValue);
        Assert.assertEquals("New header value", newValue, method.getHeader(anyHeaderName));

        // Execute
        method.call();

        // Verify
        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyHeader(anyHeaderName, newValue);
    }

    @Test
    public void addHeaderShouldReturnSameInstance() {
        Assert.assertSame(method, method.addHeader(anyHeaderName, anyHeaderValue));
    }


    @Test
    public void addParamShouldReturnSameInstance() {
        Assert.assertSame(method, method.addParam(anyParamName, anyParamValue));
    }


    @Test
    public void setBodyAsJsonShouldReturnSameInstance() {
        Assert.assertSame(method, method.setBodyAsJson(anyBody));
    }


    @Test
    public void setBodyShouldSetContentTypeHeaderToJsonByDefault() {
        method.setBodyAsJson(anyBody);

        method.call();

        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyHeader("Content-Type", "application/json");
    }

    @Test
    public void setBodyShouldRespectContentTypeWhenSet() {
        method.addHeader("Content-Type", "application/x-www-form-urlencoded");
        method.setBodyAsJson(anyBody);

        method.call();

        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyHeader("Content-Type", "application/x-www-form-urlencoded");
    }


    @Test
    public void setBodyShouldSetBodyInHttpEntity() {
        method.setBodyAsJson(anyBody);

        method.call();

        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyBody(anyBody);
    }


    @Test
    public void duringSetupWeShouldBeAbleToChangeOurMindAndClearTheJsonBodyWithoutLeavingLeftoverJsonContentTypeInTheRequest() {
        method.setBodyAsJson(anyBody);
        method.setBodyAsJson(null);

        method.call();

        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyNoHeader("Content-Type");
    }


    @Test
    public void duringSetupWeShouldBeAbleToChangeOurMindAndClearTheJsonBodyPriorToInvokingCall() {
        method.setBodyAsJson(anyBody);
        method.setBodyAsJson(null);

        method.call();

        verifyLowLevelVarargsCallAndCaptureArguments();
        verifyBody(null);
    }


    /** We don't need to test the logging itself but we do want to make sure it doesn't encounter errors when logging it. */
    @Test(expected = HttpClientErrorException.class)
    public void shouldLogHttpErrors() {
        final HttpClientErrorException error = createHttpClientError();
        throwOnApiCall(error);

        method.call();
    }


    private HttpClientErrorException createHttpClientError() {
        final String body = "Body_" + RandomStringUtils.randomAlphabetic(10);
        final String statusText = "Status_" + RandomStringUtils.randomAlphabetic(10);
        final Charset charset = Charset.defaultCharset();

        return new HttpClientErrorException(
                HttpStatus.BAD_REQUEST,
                statusText,
                body.getBytes(charset),
                charset);
    }


    private void throwOnApiCall(final RuntimeException e) {
        Mockito.when(mockTemplate.exchange(
                Matchers.any(URI.class),
                Matchers.eq(anyHttpMethod),
                Matchers.any(HttpEntity.class),
                Matchers.eq(String.class))).thenThrow(e);

    }


    private void verifyLowLevelVarargsCallAndCaptureArguments() {
        Mockito.verify(mockTemplate).exchange(
                uriCaptor.capture(),
                Matchers.eq(anyHttpMethod),
                argHttpEntity.capture(),
                Matchers.eq(String.class));
    }


    private void verifyHeader(final String name, final String value) {
        final HttpHeaders headers = argHttpEntity.getValue().getHeaders();

        final List<String> values = headers.get(name);

        Assert.assertEquals("Expected exactly 1 value for header " + name, 1, Utility.sizeOf(values));
        Assert.assertEquals("Expected value for header " + name, value, values.get(0));
    }


    private void verifyNoHeader(final String name) {
        final HttpHeaders headers = argHttpEntity.getValue().getHeaders();
        Assert.assertNull("Should not have set header " + name, headers.get(name));
    }


    private void verifyBody(final Object expected) {
        final Object actual = argHttpEntity.getValue().getBody();
        Assert.assertEquals("Body", expected, actual);
    }

    @Test
    public void restMethodShouldUseURI() {
        method.call();
        final ArgumentCaptor<URI> uriArgumentCaptor = ArgumentCaptor.forClass(URI.class);
        verify(mockTemplate).exchange(uriArgumentCaptor.capture(), Matchers.eq(anyHttpMethod), argHttpEntity.capture(), Matchers.eq(String.class));
    }

}

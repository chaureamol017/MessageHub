package com.mycomp.message.hub.core.restclient;

public interface RestMethod<T> {

    RestMethod<T> addHeader(final String name, final String value);


    RestMethod<T> addParam(final String name, final Object value);


    RestMethod<T> setBody(final Object body);

    T call();
}

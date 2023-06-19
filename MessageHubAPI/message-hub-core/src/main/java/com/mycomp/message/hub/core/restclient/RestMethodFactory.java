package com.mycomp.message.hub.core.restclient;

import com.mycomp.message.hub.core.restclient.model.Credentials;

public interface RestMethodFactory {
    
    <R> RestMethod<R> forGet( String url,  Class<R> responseType);

    
    <R> RestMethod<R> forGet( String url,  Class<R> responseType, Credentials credential);

    
    <R> RestMethod<R> forPost( String url,  Class<R> responseType, Credentials credential);

    
    <R> RestMethod<R> forPost( String url,  Class<R> responseType);

    
    <R> RestMethod<R> forPut( String url,  Class<R> responseType);

    
    <R> RestMethod<R> forDelete( String url,  Class<R> responseType);

    
    <R> RestMethod<R> forDelete( String url,  Class<R> responseType, Credentials credential);
}

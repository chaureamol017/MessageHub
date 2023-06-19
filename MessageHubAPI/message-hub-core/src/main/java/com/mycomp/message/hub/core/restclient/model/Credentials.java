package com.mycomp.message.hub.core.restclient.model;

public class Credentials {
    private String username;
    private String password;
    private String apiKey;

    public static Credentials apiCredentials(final String apiKey) {
        final Credentials credentials = new Credentials();
        credentials.setApiKey(apiKey);

        return credentials;
    }

    public static Credentials basicCredentials(final String username, final String password) {
        final Credentials credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setPassword(password);

        return credentials;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }
}

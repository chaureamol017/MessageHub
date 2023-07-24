package com.mycomp.message.hub.api.response;

public class TextMessageResponse {

    private String requestId;
    private boolean success;
    private Integer statusCode;
    private String message;

    public static TextMessageResponse createSuccess() {
        final TextMessageResponse response = new TextMessageResponse();
        response.success = true;

        return response;
    }

    public static TextMessageResponse createSuccess(final String requestId) {
        final TextMessageResponse response = new TextMessageResponse();
        response.requestId = requestId;
        response.success = true;

        return response;
    }

    public static TextMessageResponse createFail(final String requestId) {
        final TextMessageResponse response = new TextMessageResponse();

        response.requestId = requestId;
        response.success = false;

        return response;
    }

    public static TextMessageResponse createFail(final String requestId, final String message) {
        final TextMessageResponse response = new TextMessageResponse();
        response.requestId = requestId;
        response.success = false;
        response.message = message;

        return response;
    }

    public static TextMessageResponse createFail(final String requestId, final String message, final Integer statusCode) {
        final TextMessageResponse response = new TextMessageResponse();
        response.requestId = requestId;
        response.success = false;
        response.message = message;
        response.statusCode = statusCode;

        return response;
    }

    private TextMessageResponse() {
    }

    public String getRequestId() {
        return requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}

package com.mycomp.message.hub.gateway.fast2sms.v1.adapter;

import com.mycomp.message.hub.request.TextMessageRequest;
import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Fast2smsRequestParamsAdapter implements Function<TextMessageRequest, List<Pair<String, String>>> {
    public static final Fast2smsRequestParamsAdapter INSTANCE = new Fast2smsRequestParamsAdapter();

    private Fast2smsRequestParamsAdapter() {
    }

    public List<Pair<String, String>> apply(TextMessageRequest request) {
        try {
            final List<Pair<String, String>> params = new ArrayList<>();

            final String sendId = request.getSendId();
            final String message = URLEncoder.encode(request.getMessage(), "UTF-8");
            final String numbers = request.getNumbers().stream().collect(Collectors.joining(","));

            params.add(new Pair("sender_id", sendId));
            params.add(new Pair("message", message));
            params.add(new Pair("numbers", numbers));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }
}

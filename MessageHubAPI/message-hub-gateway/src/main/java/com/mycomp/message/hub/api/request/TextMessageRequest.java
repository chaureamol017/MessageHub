package com.mycomp.message.hub.api.request;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class TextMessageRequest {
    private String sendId;
    private String message;
    private String language;
    private String messageId;
    private List<String> variables;
    private List<String> numbers;

    public TextMessageRequest() {
        this.variables = Lists.newArrayList();
        this.numbers = Lists.newArrayList();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public void addVariable(String value) {
        if (this.variables == null) {
            this.variables = Lists.newArrayList();
        }
        this.variables.add(value);
    }
    public void addNumber(String value) {
        if (this.numbers == null) {
            this.numbers = Lists.newArrayList();
        }
        this.numbers.add(value);
    }

    public String pipeSeparatedVariables() {
        if (this.variables == null || this.variables.isEmpty()) {
            return "";
        }
        return this.variables.stream().collect(Collectors.joining("|"));
    }

    public String commaSeparatedNumbers() {
        if (this.numbers == null || this.numbers.isEmpty()) {
            return "";
        }
        return this.numbers.stream().collect(Collectors.joining(","));
    }
}

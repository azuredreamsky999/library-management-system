package com.library.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BaseResponse {

    private LocalDateTime timestamp;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("response_message")
    private String responseMessage;

    // Feel free to add more necessary fields such as
    // timestamp, id, etc... as needed for debugging purpose

    public BaseResponse() {
        // No-op
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}

package com.library.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Collection;

public class ResponseResult extends BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL) // Exclude when null
    @JsonProperty("query_result")
    private Collection<?> queryResult;

    public ResponseResult(Builder builder) {
        this.setStatusCode(builder.statusCode);
        this.setTimestamp(builder.timestamp);
        this.setResponseMessage(builder.responseMessage);
        this.setQueryResult(builder.queryResult);
    }

    // Static inner Builder class
    public static class Builder {
        private String responseMessage;
        private int statusCode;
        private LocalDateTime timestamp;
        private Collection<?> queryResult;

        public Builder responseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this; // Return the Builder instance for method chaining
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder queryResult(Collection<?> queryResult) {
            this.queryResult = queryResult;
            return this;
        }

        public ResponseResult build() {
            if (this.timestamp == null) {
                this.timestamp = LocalDateTime.now(); // Set a default timestamp if not provided
            }
            return new ResponseResult(this); // Return the constructed ResponseResult
        }
    }

    public Collection<?> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(Collection<?> queryResult) {
        this.queryResult = queryResult;
    }
}

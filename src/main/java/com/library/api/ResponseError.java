package com.library.api;

import java.time.LocalDateTime;

public class ResponseError extends BaseResponse {

    public ResponseError(ResponseError.Builder builder) {
        this.setStatusCode(builder.statusCode);
        this.setTimestamp(builder.timestamp);
        this.setResponseMessage(builder.responseMessage);
    }

    // Static inner Builder class
    public static class Builder {
        private String responseMessage;
        private int statusCode;
        private LocalDateTime timestamp;

        public ResponseError.Builder responseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this; // Return the Builder instance for method chaining
        }

        public ResponseError.Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseError.Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseError build() {
            if (this.timestamp == null) {
                this.timestamp = LocalDateTime.now(); // Set a default timestamp if not provided
            }
            return new ResponseError(this); // Return the constructed ResponseError
        }
    }
}

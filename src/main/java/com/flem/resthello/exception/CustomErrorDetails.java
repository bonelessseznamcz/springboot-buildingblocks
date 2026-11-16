package com.flem.resthello.exception;

import java.time.LocalDateTime;

// Simple custom error details bean
public class CustomErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String errorDetail;

    public CustomErrorDetails(LocalDateTime timestamp, String message, String errorDetail) {
        this.timestamp = timestamp;
        this.message = message;
        this.errorDetail = errorDetail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}

package org.example.portfolioapidevelopment.exception;

import lombok.Data;

@Data
public class ErrorDetails {
    private int statusCode;
    private String message;
    private long timestamp;

    public ErrorDetails(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}

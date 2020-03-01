package com.example.crud.model;

public class ApiResponse {

    private int statusCode;
    private String message;
    private String details;

    public ApiResponse(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}

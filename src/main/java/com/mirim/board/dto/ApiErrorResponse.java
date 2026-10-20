package com.mirim.board.dto;

import java.util.Map;

public class ApiErrorResponse {
    private final int status;
    private final String message;
    private final Map<String, String> error;

    public ApiErrorResponse(int status, String message, Map<String, String> error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public Map<String, String> getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}

package com.hcmute.myanime.dto;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ResponseDTO {
    private HttpStatus status;
    private String message;
    private Map<?, ?> data;

    public ResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        data = new HashMap<String, Object>();
    }

    public ResponseDTO(HttpStatus status, String message, Map<?, ?> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.hcmute.myanime.dto;

import org.springframework.http.HttpStatus;

public class SuccessResponseDTO {
    private HttpStatus status;
    private String message;

    public SuccessResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getSuccessMessage() {
        return message;
    }

    public void setSuccessMessage(String successMessage) {
        this.message = successMessage;
    }
}

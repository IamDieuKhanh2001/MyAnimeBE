package com.hcmute.myanime.exception;

public class BadRequestException extends IllegalStateException{
    public BadRequestException(String message){
        super(message);
    }
}

package com.sysdes.robottracker.core.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception{
    private final String message;
    public ResourceNotFoundException(String msg){
        super();
        this.message = msg;
    }
}

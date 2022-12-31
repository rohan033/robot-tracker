package com.sysdes.rts.application.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class InvalidArgumentException extends Exception{
    private final String[] messages;
    public InvalidArgumentException(@NonNull String[] messages){
        super("Invalid arguments passed");
        this.messages = messages;
    }
}

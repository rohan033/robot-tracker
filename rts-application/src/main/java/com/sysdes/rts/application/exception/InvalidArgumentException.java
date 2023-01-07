package com.sysdes.rts.application.exception;

import lombok.NonNull;

import java.util.Arrays;

public class InvalidArgumentException extends Exception{
    public InvalidArgumentException(@NonNull String[] messages){
        super(Arrays.toString(messages));
    }
}

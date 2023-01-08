package com.sysdes.rts.application.exception;

import lombok.NonNull;

public class IllegalStateException extends Exception{
    public IllegalStateException(@NonNull String msg){
        super(msg);
    }
}

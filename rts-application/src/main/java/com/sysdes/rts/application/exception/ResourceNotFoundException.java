package com.sysdes.rts.application.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ResourceNotFoundException extends Exception {
    private final String message;

    public ResourceNotFoundException(@NonNull String msg) {
        super();
        this.message = msg;
    }
}

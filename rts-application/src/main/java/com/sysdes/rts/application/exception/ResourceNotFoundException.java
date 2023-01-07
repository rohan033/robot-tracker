package com.sysdes.rts.application.exception;

import lombok.NonNull;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(@NonNull String msg) {
        super(msg);
    }
}

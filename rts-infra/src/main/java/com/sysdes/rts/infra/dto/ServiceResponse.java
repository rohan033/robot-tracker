package com.sysdes.rts.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> implements Serializable {
    private T data;
    private Error error;

    public ServiceResponse(T data){
        this.data = data;
    }
}

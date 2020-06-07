package com.myservice.mainpack.exception;

import com.myservice.mainpack.dto.RestResponce;


public class ConflictException extends BasicRestResponceException {

    public ConflictException(RestResponce restResponse) {
        super(restResponse);
    }

}
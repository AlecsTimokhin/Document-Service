package com.myservice.mainpack.exception;

import com.myservice.mainpack.dto.RestResponce;


public class BadValidateException extends BasicRestResponceException {

    public BadValidateException(RestResponce restResponse) {
        super(restResponse);
    }

}
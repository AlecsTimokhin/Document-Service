package com.myservice.mainpack.exception;

import com.myservice.mainpack.dto.RestResponce;


public class ProcessException extends BasicRestResponceException {

    public ProcessException(RestResponce restResponse) {
        super(restResponse);
    }

}
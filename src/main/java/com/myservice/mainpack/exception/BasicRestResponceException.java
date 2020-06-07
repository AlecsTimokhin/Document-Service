package com.myservice.mainpack.exception;

import com.myservice.mainpack.dto.RestResponce;


public class BasicRestResponceException extends RuntimeException {

    private RestResponce restResponce;


    public BasicRestResponceException(RestResponce restResponse) {
        //super(restResponse.getMessage());
        this.restResponce = restResponse;
    }


    public RestResponce getRestResponse() { return restResponce; }

    public void setRestResponse(RestResponce restResponse) { this.restResponce = restResponse; }

}
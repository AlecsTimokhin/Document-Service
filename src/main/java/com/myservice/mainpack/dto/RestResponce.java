package com.myservice.mainpack.dto;

public class RestResponce {

    private String message;
    private String status;


    public RestResponce(){}

    public RestResponce(String message, String status){
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

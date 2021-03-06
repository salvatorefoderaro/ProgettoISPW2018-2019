package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ResponseEnum;

public class ResponseBean {
    private ResponseEnum response;
    private String message;

    public ResponseBean(){

    }

    public ResponseBean(ResponseEnum response, String message) {
        this.response = response;
        this.message = message;
    }

    public ResponseEnum getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public void setResponse(ResponseEnum response) {
        this.response = response;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

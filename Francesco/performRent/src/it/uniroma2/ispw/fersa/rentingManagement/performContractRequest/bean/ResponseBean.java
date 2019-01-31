package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ResponseEnum;
import javafx.scene.Scene;

public class ResponseBean {
    private ResponseEnum response;
    private String message;

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
}

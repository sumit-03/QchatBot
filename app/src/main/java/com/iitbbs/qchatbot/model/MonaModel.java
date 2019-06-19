package com.iitbbs.qchatbot.model;

public class MonaModel {
    private String response;

    public MonaModel(String response) {
        this.response = response;
    }
    public MonaModel() {}

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

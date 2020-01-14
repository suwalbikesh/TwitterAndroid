package com.example.twitter.server_responses;

public class SignUpResponse {
    private String status;
    private String usertoken;

    public SignUpResponse(String status, String usertoken) {
        this.status = status;
        this.usertoken = usertoken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }
}

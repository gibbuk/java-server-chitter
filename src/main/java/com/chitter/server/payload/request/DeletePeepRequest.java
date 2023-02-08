package com.chitter.server.payload.request;

import javax.validation.constraints.NotBlank;

public class DeletePeepRequest {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String peepId;

    public DeletePeepRequest() {
    }

    public DeletePeepRequest(String userId, String password, String peepId) {
        this.userId = userId;
        this.password = password;
        this.peepId = peepId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPeepId() {
        return peepId;
    }

    public void setPeepId(String peepId) {
        this.peepId = peepId;
    }
}

package com.chitter.server.payload.request;

import javax.validation.constraints.NotBlank;

public class PeepRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String content;

    public PeepRequest() {}

    public PeepRequest(String userId, String password, String content) {
        this.userId = userId;
        this.password = password;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getContent() {
        return content;
    }
}
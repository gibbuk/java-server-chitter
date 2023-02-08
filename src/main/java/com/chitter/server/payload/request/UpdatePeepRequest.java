package com.chitter.server.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdatePeepRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String peepId;

    @NotBlank
    private String content;

    public UpdatePeepRequest(){}

    public UpdatePeepRequest(String userId, String password, String peepId, String content) {
        this.userId = userId;
        this.password = password;
        this.peepId = peepId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getPeepId() {
        return peepId;
    }

    public String getContent() {
        return content;
    }
}

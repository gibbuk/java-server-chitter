package com.chitter.server.payload.response;

import com.chitter.server.model.User;

public class LoginResponse {

    private User user;

    public LoginResponse() {}

    public LoginResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

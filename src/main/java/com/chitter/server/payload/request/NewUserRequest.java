package com.chitter.server.payload.request;

import com.chitter.server.model.User;

public class NewUserRequest {

    private User newUser;

    public NewUserRequest(){}

    public NewUserRequest(User newUser) {
        this.newUser = newUser;
    }

    public User getNewUser() {
        return newUser;
    }
}

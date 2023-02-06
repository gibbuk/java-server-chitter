package com.chitter.server.payload.request;

import com.chitter.server.model.Peep;
import com.chitter.server.model.User;

public class PeepRequest {

    private User user;

    private Peep peep;

    public PeepRequest() {}

    public PeepRequest(User user, Peep peep) {
        this.user = user;
        this.peep = peep;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Peep getPeep() {
        return peep;
    }

    public void setPeep(Peep peep) {
        this.peep = peep;
    }
}
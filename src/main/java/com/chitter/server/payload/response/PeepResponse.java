package com.chitter.server.payload.response;

import com.chitter.server.model.Peep;

public class PeepResponse {

    private String message;

    private Peep peep;

    public PeepResponse(String message, Peep peep){
        this.message = message;
        this.peep = peep;
    }

    public String getMessage() {
        return message;
    }

    public Peep getPeep() {
        return peep;
    }
}

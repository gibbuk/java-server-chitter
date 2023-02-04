package com.chitter.server.response;

import com.chitter.server.model.Peep;

public class ResponseObject {

    private String message;

    private Peep peep;

    public ResponseObject(String message, Peep peep){
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

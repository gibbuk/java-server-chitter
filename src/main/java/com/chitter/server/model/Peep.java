package com.chitter.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "peeps")
public class Peep {

    @Id
    private String _id;
    private String username;
    private String realName;
    private String content;
    private String dateCreated;

    public Peep(){}

    public Peep(String username, String realName, String content, String dateCreated){

    }



}

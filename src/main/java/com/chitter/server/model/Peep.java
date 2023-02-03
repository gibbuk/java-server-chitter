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
        this.username = username;
        this.realName = realName;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}

package com.chitter.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "peeps")
public class Peep {

    @Id
    private String id;
    private String userId;
    private String content;
    private String dateCreated;

    public Peep(){}

    public Peep(String userId, String content, String dateCreated){
        this.userId = userId;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public String getId(){
        return this.id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString(){
        return "Peep [_id: "+this.id
                +", username: "+this.userId
                +", content: "+this.content
                +", dateCreated: "+this.dateCreated+"]";
    }
}

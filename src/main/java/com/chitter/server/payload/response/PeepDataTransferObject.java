package com.chitter.server.payload.response;

public class PeepDataTransferObject {

    private String peepId;
    private String userId;
    private String username;
    private String realname;
    private String content;
    private String date;

    public PeepDataTransferObject() {}

    public PeepDataTransferObject(String peepId, String userId, String username, String realname, String content, String date) {
        this.peepId = peepId;
        this.userId = userId;
        this.username = username;
        this.realname = realname;
        this.content = content;
        this.date = date;
    }

    public String getPeepId() {
        return peepId;
    }

    public void setPeepId(String peepId) {
        this.peepId = peepId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

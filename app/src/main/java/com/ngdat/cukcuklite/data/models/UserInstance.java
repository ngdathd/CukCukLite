package com.ngdat.cukcuklite.data.models;

public class UserInstance {

    private static final UserInstance ourInstance = new UserInstance();

    private String uid;
    private String key;
    private String name;
    private String email;

    public static UserInstance getInstance() {
        return ourInstance;
    }

    private UserInstance() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

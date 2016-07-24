package com.bigspawn;

/**
 * Created by bigsp on 24.07.2016.
 */
public class User {
    private String name;
    private String password;
    private String info;

    public User(String name, String password, String info) {
        this.name = name;
        this.password = password;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getInfo() {
        return info;
    }
}

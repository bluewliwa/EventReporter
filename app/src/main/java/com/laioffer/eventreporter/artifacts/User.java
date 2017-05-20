package com.laioffer.eventreporter.artifacts;

/**
 * Created by LEI_D on 4/10/2017.
 */

public class User {
    private String username;
    private String password;
    private long time;

    public String getUsername() {
        return username;
    }

    public void SetUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User(final String username, final String password, final long timeStamp) {
        this.username = username;
        this.password = password;
        this.time = timeStamp;
    }
}

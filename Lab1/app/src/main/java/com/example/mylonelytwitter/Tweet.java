package com.example.mylonelytwitter;

import java.util.Date;

public abstract class Tweet implements Tweetable {
    private Date date;
    private String message;

    Tweet(String msg) {
        setMessage(msg);
        date = new Date();
    }

    Tweet(Date d, String msg) {
        setMessage(msg);
        date = d;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        if (msg.length() > 140) {
            this.message = msg.substring(0, 140);
        } else {
            this.message = msg;
        }
    }

    public abstract Boolean isImportant();
}

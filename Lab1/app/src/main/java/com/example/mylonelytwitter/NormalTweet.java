package com.example.mylonelytwitter;

import java.util.Date;

public class NormalTweet extends Tweet {
    NormalTweet(String msg) {
        super(msg);
    }

    NormalTweet(Date d, String msg) {
        super(d, msg);
    }

    @Override
    public Boolean isImportant() {
        return false;
    }
}

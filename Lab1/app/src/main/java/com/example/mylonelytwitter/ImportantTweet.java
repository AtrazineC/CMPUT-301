package com.example.mylonelytwitter;

import java.util.Date;

public class ImportantTweet extends Tweet {
    ImportantTweet(String msg) {
        super(msg);
    }

    ImportantTweet(Date d, String msg) {
        super(d, msg);
    }

    @Override
    public Boolean isImportant() {
        return true;
    }
}

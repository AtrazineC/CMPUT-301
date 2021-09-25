package com.example.mylonelytwitter;

import java.util.Date;

public class Sad extends Mood {
    private static String moodName = "Sad";

    Sad() {
        super(moodName);
    }

    Sad(Date date) {
        super(date, moodName);
    }
}

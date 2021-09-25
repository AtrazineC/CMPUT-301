package com.example.mylonelytwitter;

import java.util.Date;

public class Happy extends Mood {
    private static String moodName = "Happy";

    Happy() {
        super(moodName);
    }

    Happy(Date date) {
        super(date, moodName);
    }
}

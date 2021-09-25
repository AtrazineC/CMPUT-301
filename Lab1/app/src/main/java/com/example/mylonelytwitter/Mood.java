package com.example.mylonelytwitter;

import java.util.Date;

public abstract class Mood {
    private Date date;
    private String mood;

    Mood(String mood) {
        this.date = new Date();
        this.mood = mood;
    }

    Mood(Date date, String mood) {
        this.date = date;
        this.mood = mood;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }
}

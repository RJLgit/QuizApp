package com.example.android.myquizapp;

import android.os.Build;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GlobalScoreUpdated {
    private Timestamp sport;
    private Timestamp films;
    private Timestamp geography;
    private Timestamp history;
    private Timestamp music;
    private Timestamp nature;
    private Timestamp people;
    private Timestamp pictures;
    private Timestamp technology;
    private Timestamp tv;
    private Timestamp ultimate;
    private static final String TAG = "GlobalScoreUpdated";

    public GlobalScoreUpdated() {
    }



    public GlobalScoreUpdated(Timestamp sport, Timestamp films, Timestamp geography, Timestamp history, Timestamp music, Timestamp nature, Timestamp people, Timestamp pictures, Timestamp technology, Timestamp tv, Timestamp ultimate) {
        this.sport = sport;
        this.films = films;
        this.geography = geography;
        this.history = history;
        this.music = music;
        this.nature = nature;
        this.people = people;
        this.pictures = pictures;
        this.technology = technology;
        this.tv = tv;
        this.ultimate = ultimate;
    }

    public Timestamp getSport() {
        return sport;
    }

    public void setSport(Timestamp sport) {
        this.sport = sport;
    }

    public Timestamp getFilms() {
        return films;
    }

    public void setFilms(Timestamp films) {
        this.films = films;
    }

    public Timestamp getGeography() {
        return geography;
    }

    public void setGeography(Timestamp geography) {
        this.geography = geography;
    }

    public Timestamp getHistory() {
        return history;
    }

    public void setHistory(Timestamp history) {
        this.history = history;
    }

    public Timestamp getMusic() {
        return music;
    }

    public void setMusic(Timestamp music) {
        this.music = music;
    }

    public Timestamp getNature() {
        return nature;
    }

    public void setNature(Timestamp nature) {
        this.nature = nature;
    }

    public Timestamp getPeople() {
        return people;
    }

    public void setPeople(Timestamp people) {
        this.people = people;
    }

    public Timestamp getPictures() {
        return pictures;
    }

    public void setPictures(Timestamp pictures) {
        this.pictures = pictures;
    }

    public Timestamp getTechnology() {
        return technology;
    }

    public void setTechnology(Timestamp technology) {
        this.technology = technology;
    }

    public Timestamp getTv() {
        return tv;
    }

    public void setTv(Timestamp tv) {
        this.tv = tv;
    }

    public Timestamp getUltimate() {
        return ultimate;
    }

    public void setUltimate(Timestamp ultimate) {
        this.ultimate = ultimate;
    }

    public String getUpdatedSince() {
        String result = "";
        ArrayList<String> cats = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 26) {
            LocalDate current = LocalDate.now();
            LocalDate twoDaysAgo = current.minusDays(2);
            Date twoDaysAgoDate = Date.from(twoDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Log.d(TAG, "getUpdatedSince: ");
            if (sport != null && sport.toDate().after(twoDaysAgoDate)) {
                cats.add("Sport");
            }
            if (tv != null && tv.toDate().after(twoDaysAgoDate)) {
                cats.add("Tv");
            }
            if (ultimate != null && ultimate.toDate().after(twoDaysAgoDate)) {
                cats.add("Ultimate");
            }
            if (films != null && films.toDate().after(twoDaysAgoDate)) {
                cats.add("Films");
            }
            if (technology != null && technology.toDate().after(twoDaysAgoDate)) {
                cats.add("Technology");
            }
            if (pictures != null && pictures.toDate().after(twoDaysAgoDate)) {
                cats.add("Pictures");
            }
            if (people != null && people.toDate().after(twoDaysAgoDate)) {
                cats.add("People");
            }
            if (nature != null && nature.toDate().after(twoDaysAgoDate)) {
                cats.add("Nature");
            }
            if (music != null && music.toDate().after(twoDaysAgoDate)) {
                cats.add("Music");
            }
            if (history != null && history.toDate().after(twoDaysAgoDate)) {
                cats.add("History");
            }
            if (geography != null && geography.toDate().after(twoDaysAgoDate)) {
                cats.add("Geography");
            }
        } else {
            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.DATE, -2);
            Date twoDaysAgoDate = cal.getTime();
            Log.d(TAG, "getUpdatedSince: " + twoDaysAgoDate);
            if (sport != null && sport.toDate().after(twoDaysAgoDate)) {
                cats.add("Sport");
            }
            if (tv != null && tv.toDate().after(twoDaysAgoDate)) {
                cats.add("Tv");
            }
            if (ultimate != null && ultimate.toDate().after(twoDaysAgoDate)) {
                cats.add("Ultimate");
            }
            if (films != null && films.toDate().after(twoDaysAgoDate)) {
                cats.add("Films");
            }
            if (technology != null && technology.toDate().after(twoDaysAgoDate)) {
                cats.add("Technology");
            }
            if (pictures != null && pictures.toDate().after(twoDaysAgoDate)) {
                cats.add("Pictures");
            }
            if (people != null && people.toDate().after(twoDaysAgoDate)) {
                cats.add("People");
            }
            if (nature != null && nature.toDate().after(twoDaysAgoDate)) {
                cats.add("Nature");
            }
            if (music != null && music.toDate().after(twoDaysAgoDate)) {
                cats.add("Music");
            }
            if (history != null && history.toDate().after(twoDaysAgoDate)) {
                cats.add("History");
            }
            if (geography != null && geography.toDate().after(twoDaysAgoDate)) {
                cats.add("Geography");
            }
        }
        if (cats.size() == 0) {
            result = "No top scores have been set in the last two days. Can you set one?";
        } else {
            result = "The following had top scores in the last two days: ";
            for (int i = 0; i < cats.size(); i++) {
                result = result + "\n";
                result = result + cats.get(i);

            }
        }
        Log.d(TAG, "getUpdatedSince: " + result);
        return result;
    }
}

package com.example.android.myquizapp;

import com.google.firebase.Timestamp;

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
}

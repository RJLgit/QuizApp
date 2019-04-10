package com.example.android.myquizapp;

public class TopScores {
    private int Sport;
    private int Music;
    private int Nature;
    private int History;
    private int Geography;
    private int Technology;
    private int People;
    private int Pictures;
    private int Films;
    private int TV;

    public TopScores() {
    }

    public TopScores(int sport, int music, int nature, int history, int geography, int technology, int people, int pictures, int films, int TV) {
        Sport = sport;
        Music = music;
        Nature = nature;
        History = history;
        Geography = geography;
        Technology = technology;
        People = people;
        Pictures = pictures;
        Films = films;
        this.TV = TV;
    }
    public int getScoreByCategory(String cat) {
        switch (cat) {
            case "Sport":
                return getSport();
            case "Music":
                return getMusic();
            case "Nature":
                return getNature();
            case "History":
                return getHistory();
            case "Geography":
                return getGeography();
            case "Technology":
                return getTechnology();
            case "People":
                return getPeople();
            case "Pictures":
                return getPictures();
            case "Films":
                return getFilms();
            case "TV":
                return getTV();
                default:
                    return 0;
        }
    }

    public int getSport() {
        return Sport;
    }

    public void setSport(int sport) {
        Sport = sport;
    }

    public int getMusic() {
        return Music;
    }

    public void setMusic(int music) {
        Music = music;
    }

    public int getNature() {
        return Nature;
    }

    public void setNature(int nature) {
        Nature = nature;
    }

    public int getHistory() {
        return History;
    }

    public void setHistory(int history) {
        History = history;
    }

    public int getGeography() {
        return Geography;
    }

    public void setGeography(int geography) {
        Geography = geography;
    }

    public int getTechnology() {
        return Technology;
    }

    public void setTechnology(int technology) {
        Technology = technology;
    }

    public int getPeople() {
        return People;
    }

    public void setPeople(int people) {
        People = people;
    }

    public int getPictures() {
        return Pictures;
    }

    public void setPictures(int pictures) {
        Pictures = pictures;
    }

    public int getFilms() {
        return Films;
    }

    public void setFilms(int films) {
        Films = films;
    }

    public int getTV() {
        return TV;
    }

    public void setTV(int TV) {
        this.TV = TV;
    }
}

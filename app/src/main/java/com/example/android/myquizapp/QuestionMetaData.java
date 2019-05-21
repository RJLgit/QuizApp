package com.example.android.myquizapp;

public class QuestionMetaData {
    private int Films;
    private int TV;
    private int Geography;
    private int History;
    private int Music;
    private int Pictures;
    private int Nature;
    private int Sport;
    private int Technology;
    private int People;

    public QuestionMetaData(int films, int TV, int geography, int history, int music, int pictures, int nature, int sport, int technology, int people) {
        Films = films;
        this.TV = TV;
        Geography = geography;
        History = history;
        Music = music;
        Pictures = pictures;
        Nature = nature;
        Sport = sport;
        Technology = technology;
        People = people;
    }

    public QuestionMetaData() {
    }

    public int getNumQuestions(String category) {
        switch (category) {
            case "Films":
                return getFilms();
            case "Nature":
                return getNature();
            case "Music":
                return getMusic();
            case "Pictures":
                return getPictures();
            case "People":
                return getPeople();
            case "History":
                return getHistory();
            case "Geography":
                return getGeography();
            case "Technology":
                return getTechnology();
            case "TV":
                return getTV();
            case "Sport":
                return getSport();
                default:
                    return 20;
        }
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

    public int getGeography() {
        return Geography;
    }

    public void setGeography(int geography) {
        Geography = geography;
    }

    public int getHistory() {
        return History;
    }

    public void setHistory(int history) {
        History = history;
    }

    public int getMusic() {
        return Music;
    }

    public void setMusic(int music) {
        Music = music;
    }

    public int getPictures() {
        return Pictures;
    }

    public void setPictures(int pictures) {
        Pictures = pictures;
    }

    public int getNature() {
        return Nature;
    }

    public void setNature(int nature) {
        Nature = nature;
    }

    public int getSport() {
        return Sport;
    }

    public void setSport(int sport) {
        Sport = sport;
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
}

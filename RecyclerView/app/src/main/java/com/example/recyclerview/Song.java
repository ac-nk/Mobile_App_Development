package com.example.recyclerview;

public class Song {
    private String title, artist;
    private int year;

    public Song() {
    }
    public Song(String t, String a, int y) {
        this.title = t;
        this.artist = a;
        this.year = y;
    }

    public String getTitle() {
        return this.title;
    }
    public String getArtist() {
        return this.artist;
    }
    public int getYear() {
        return this.year;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public void setYear(int year) {
        this.year = year;
    }
}

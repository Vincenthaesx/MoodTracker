package com.example.megaport.moodtracker.Model;

import java.util.Hashtable;


public class MoodData {
    public String time;
    public String message;
    public float sizeCard;
    public int colorCard;


    MoodData(String time, String message, int colorCard, float sizeCard) {
        this.time = time;
        this.message = message;
        this.colorCard = colorCard;
        this.sizeCard = sizeCard;


    }

    // this method take a int and return a String depending on int parameter
    public String getDate(int time) {
        Hashtable<Integer, String> dayAgo = new Hashtable<>();
        dayAgo.put(7, "Il y a une semaine");
        dayAgo.put(6, "Il y a 6 jours");
        dayAgo.put(5, "Il y a 5 jours");
        dayAgo.put(4, "Il y a 4 jours");
        dayAgo.put(3, "Il y a 3 jours");
        dayAgo.put(2, "Avant-hier");
        dayAgo.put(1, "Hier");

        return dayAgo.get(time);
    }
}
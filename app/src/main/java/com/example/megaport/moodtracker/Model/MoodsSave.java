package com.example.megaport.moodtracker.Model;

//created by vincent, 25/05/2018

public class MoodsSave {

    //variable

    private int moodsNumber = 3;
    private String comment = null;

    public MoodsSave() {
    }

    public int getMoodsNumber() {
        return moodsNumber;
    }

    public void setMoodsNumber(int moodsNumber) {
        this.moodsNumber = moodsNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return " toString Moods Save: moods number = " + moodsNumber + " comment = " + comment + " . ";
    }

}

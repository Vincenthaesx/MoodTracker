package com.example.megaport.moodtracker.Model;

import java.util.ArrayList;

//created by vincent, 26/07/2018

public class History {

    private ArrayList<MoodsSave> historyList = new ArrayList<>();

    //constructor
    public History() {

    }

    public void addList(MoodsSave MoodaddList){

        historyList.add(MoodaddList);


    }

    public MoodsSave getList(int i){

        return historyList.get(i);
    }

    public void removeList(int i){

        historyList.remove(i);

    }

    public int getSizeList(){

        return historyList.size();
    }

    public String toString(){

        int size = historyList.size();
        int i = 0;
        StringBuilder str = new StringBuilder( " size " + historyList.size() + " // " );

        while (i < size){

            str.append( historyList.get( i ).toString() ).append( " i = " ).append( i ).append( " // " );
            i++;
        }

        return " toString History = " + str;
    }


}

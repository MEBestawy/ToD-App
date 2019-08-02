package com.d2.truth_or_dare;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class DataCollector implements Parcelable {

    private ArrayList<String> truths;
    private ArrayList<String> dares;
    private ArrayList<String> names;

    public DataCollector() {
        this.truths = new ArrayList<>();
        this.dares = new ArrayList<>();
        this.names = new ArrayList<>();
    }

    public void addName(String name) {
        if (!(name.isEmpty() || this.names.contains(name))) {
            this.names.add(name.toUpperCase());
        }
    }

    public void addDare(String dare) {
        addToList(this.dares, dare);
    }

    public void addTruth(String truth) {
        addToList(this.truths, truth);
    }

    private void addToList(ArrayList list, String value) {
        if (!value.trim().equals("")) {
            list.add(value.substring(0,1).toUpperCase() + value.substring(1));
        }
    }


    public String getAName() {
        if (names.isEmpty()) {
            return "";
        }

        Random rand = new Random();
        int i = rand.nextInt(names.size());
        return names.get(i);
    }

    public String getADare() {
        return getTheValue(this.dares);
    }

    public String getATruth() {
        return getTheValue(this.truths);
    }

    private String getTheValue(ArrayList list) {
        if (list.isEmpty()) {
            return "";
        }

        Random rand = new Random();
        int i = rand.nextInt(list.size());
        String theValue = list.get(i).toString();
        list.remove(theValue);
        return theValue;
    }

    public boolean checkNameIsUnique(String name) {
        return !this.names.contains(name.toUpperCase());
    }

    public boolean checkIfDaresEmpty() {
        return checkIfListEmpty(this.dares);
    }
    public boolean checkIfTruthsEmpty() {
        return checkIfListEmpty(this.truths);
    }

    private boolean checkIfListEmpty(ArrayList list) {
        return list.isEmpty();
    }


    public void clear() {
        this.truths.clear();
        this.names.clear();
        this.dares.clear();
    }


    // Parcelable code
    protected DataCollector(Parcel in) {
        truths = in.createStringArrayList();
        dares = in.createStringArrayList();
        names = in.createStringArrayList();
    }

    public static final Creator<DataCollector> CREATOR = new Creator<DataCollector>() {
        @Override
        public DataCollector createFromParcel(Parcel in) {
            return new DataCollector(in);
        }

        @Override
        public DataCollector[] newArray(int size) {
            return new DataCollector[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(truths);
        parcel.writeStringList(dares);
        parcel.writeStringList(names);
    }
}

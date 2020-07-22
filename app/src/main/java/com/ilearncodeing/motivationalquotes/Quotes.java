package com.ilearncodeing.motivationalquotes;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quotes {

    @SerializedName("quotes")
    @Expose
    private ArrayList<String> quotes = null;

    public ArrayList<String> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<String> quotes) {
        this.quotes = quotes;
    }

}
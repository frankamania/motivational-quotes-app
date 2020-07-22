
package com.ilearncodeing.motivationalquotes;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categorys {

    @SerializedName("category")
    @Expose
    private ArrayList<Category> category = null;

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

}

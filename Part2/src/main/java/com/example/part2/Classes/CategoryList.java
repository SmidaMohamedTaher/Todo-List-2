package com.example.part2.Classes;

import java.util.ArrayList;

public class CategoryList {

    private ArrayList<Category> categories;

    public CategoryList() {
        categories = new ArrayList<>();
    }

    public CategoryList(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Category findTheCatigory(String catigory){
        for (Category cat : categories) {
            if (cat.getCategoryName().equals(catigory)) {
                return cat ;
            }
        }
        return null;
    }
}

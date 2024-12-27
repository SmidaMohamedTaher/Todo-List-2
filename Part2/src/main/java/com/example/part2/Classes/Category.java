package com.example.part2.Classes;

import com.example.part2.DataBases.Category_Data_Base_methode;

public class Category {

    private int idCategory;
    private final String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(int idCategory, String categoryName) {
        this.idCategory = idCategory;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + this.idCategory +
                ", category='" + this.categoryName
                + '\'' +
                '}';
    }
}

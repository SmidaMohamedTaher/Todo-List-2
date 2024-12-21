package com.example.part2.Classes;

public class Category {

    private int idCategory;
    private final String categoryName;

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
}

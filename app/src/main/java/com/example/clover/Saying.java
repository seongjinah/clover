package com.example.clover;

public class Saying {
    private String saying;
    private String author;
   // private String category;

    Saying(){
        saying="";
        author="";
        //category="";
    }

    Saying(String saying, String author/*, String category*/){
        this.saying=saying;
        this.author=author;
        //this.category=category;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
/*
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }*/
}

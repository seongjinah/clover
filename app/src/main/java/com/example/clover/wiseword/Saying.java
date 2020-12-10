package com.example.clover.wiseword;

import java.util.ArrayList;
import java.util.List;

public class Saying {
    private String category;
    private String saying;
    private String author;
    private int star;
    private List<String> id;
   // private String category;

    Saying(){
        saying="";
        author="";
        category = "";
        star=0;
        id = new ArrayList<>();
        //category="";
    }

    public Saying(String saying, String author,Integer star/*, String category*/){
        this.saying=saying;
        this.author=author;
        this.category = "";
        this.star = star;
        id = new ArrayList<>();
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

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public Integer getstar() {
        return star;
    }

    public void setstar(Integer star) {
        this.star = star;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public boolean addLover(String loverID) {
        if (id.remove(loverID) == false) {
            id.add(loverID);
            return true;
        }
        return false;
    }
    /*
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }*/
}

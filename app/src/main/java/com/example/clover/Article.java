package com.example.clover;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String id;
    private String date;
    private String title;
    private String write;

    Article() {
        id = "";
        date = "";
        title = "";
        write = "";
    }

    Article(String id, String date, String title, String write) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.write = write;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getwrite() {
        return write;
    }

    public void setwrite(String write) {
        this.write = write;
    }


}

package com.videoapp;

public class Video {
    public String name;
    public String id;
    public boolean visible;

    public Video(String nameOfMovie){
        this.name=nameOfMovie;
        this.id=nameOfMovie+"_id_";
        this.visible= true;
    }
}
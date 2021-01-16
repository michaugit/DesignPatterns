package com.videoapp;

public class Video {
    public String videoName;
    public String userName;
    private Visibility visibility;

    public Video(String userName, String videoName){
        this.videoName = videoName;
        this.userName = userName;
        this.visibility = Visibility.PUBLIC;
    }

    public Video(String userName, String videoName, Visibility visibility){
        this.videoName = videoName;
        this.userName = userName;
        this.visibility = visibility;
    }

    public String checkType(){
        if (this.visibility == Visibility.PUBLIC){
            return "public";
        }else{
            return "private";
        }
    }

    public String getTypeChanged(){
        if (this.visibility == Visibility.PUBLIC){
            return "private";
        }else{
            return "public";
        }
    }

    public void changeVisibility(){
        if (this.visibility == Visibility.PUBLIC){
            this.visibility = Visibility.PRIVATE;
        }else{
            this.visibility = Visibility.PUBLIC;
        }
    }

    public Visibility getVisibility(){
        return visibility;
    }


    public enum Visibility {
        PUBLIC,
        PRIVATE;
    }
}

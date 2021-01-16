package com.videoapp;

public class Video {
    public String videoName;
    public String userName;
    public boolean visible;

    public Video(String userName, String videoName){
        this.videoName = videoName;
        this.userName = userName;
        this.visible= true;
    }

    public String checkType(){
        if (this.visible == true){
            return "public";
        }else{
            return "private";
        }
    }

    public String getTypeChanged(){
        if (this.visible == true){
            return "private";
        }else{
            return "public";
        }
    }

    public void changeVisibility(){
        this.visible = !this.visible;
    }
}

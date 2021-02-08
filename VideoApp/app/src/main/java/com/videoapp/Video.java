package com.videoapp;

public class Video {
    public String videoName;
    public String userName;
    private Visibility visibility;

    private Video(String userName, String videoName, Visibility visibility) {
        this.videoName = videoName;
        this.userName = userName;
        this.visibility = visibility;
    }

    public String checkType() {
        if (this.visibility == Visibility.PUBLIC) {
            return "public";
        } else {
            return "private";
        }
    }

    public String getTypeChanged() {
        if (this.visibility == Visibility.PUBLIC) {
            return "private";
        } else {
            return "public";
        }
    }

    public void changeVisibility() {
        if (this.visibility == Visibility.PUBLIC) {
            this.visibility = Visibility.PRIVATE;
        } else {
            this.visibility = Visibility.PUBLIC;
        }
    }

    public Visibility getVisibility() {
        return visibility;
    }


    public static final class VideoBuilder {
        private String videoName;
        private String userName;
        private Visibility visibility;


        public VideoBuilder videoName(String videoName){
            this.videoName = videoName;
            return this;
        }

        public VideoBuilder userName(String userName){
            this.userName = userName;
            return this;
        }

        public VideoBuilder visibility(Visibility visibility){
            this.visibility = visibility;
            return this;
        }


        public Video build() {
            if (videoName == null || videoName.isEmpty()) {
                throw new IllegalArgumentException("videoName must be non-empty value");
            }
            if (userName == null) {
                userName = "";
            }
            if (visibility == null) {
                visibility = visibility.PUBLIC;
            }
            return new Video(userName, videoName, visibility);
        }
    }

    public static VideoBuilder builder() {
        return new VideoBuilder();
    }


    public enum Visibility {
        PUBLIC,
        PRIVATE;
    }
}

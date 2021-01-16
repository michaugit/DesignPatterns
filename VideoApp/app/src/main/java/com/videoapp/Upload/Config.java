package com.videoapp.Upload;

public class Config {
    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "http://10.0.2.2:80/upload.php";

    public static final String LOGIN_URL = "http://10.0.2.2:8080/dp_server_test_war_exploded/login";
    public static final String DELETE_VIDEO_URL = "http://10.0.2.2:8080/dp_server_test_war_exploded/delete";
    public static final String UPLOAD_VIDEO_URL = "http://10.0.2.2:8080/dp_server_test_war_exploded/upload";
    public static final String VIDEO_VISIBILITY_URL = "http://10.0.2.2:8080/dp_server_test_war_exploded/change-visibility?videoname=";
    public static final String BASE_URL = "http://10.0.2.2:8080/dp_server_test_war_exploded/";
    public static final String STREAM_VIDEO_URL = "http://10.0.2.2:8080/dp_server_test_war_exploded/stream?videoname=";
    public static final String UPLOAD_FINISHED = "http://10.0.2.2:8080/dp_server_test_war_exploded/upload-finished?filename=";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "AndroidFileUpload";
}

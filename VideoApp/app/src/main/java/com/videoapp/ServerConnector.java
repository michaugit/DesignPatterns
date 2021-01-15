package com.videoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.videoapp.Upload.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerConnector{

    private static String staticUserName = null;

    public static int login_URL(String userName, String userPass) throws IOException {
        userPass = generateHashPassword(userPass);
        String urlParameters  = "username="+userName+"&userpass="+userPass;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        URL url            = new URL(Config.LOGIN_URL);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod("POST" );
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString( postDataLength));
        conn.setUseCaches(false);

        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }
        int responseCode = conn.getResponseCode();
        if (responseCode == 200){
            staticUserName = userName;
        }
        conn.disconnect();

        return responseCode;
    }

    public static String getUserName (){
        return staticUserName;
    }

    public static String playVideo_URL(String videoName){

        String uri = null;
        JSONObject uriJSON = doJSONGet(Config.STREAM_VIDEO_URL + videoName);
        // TODO uri from JSON
        // { "uri" : "http://link"}
        try {
            uri = uriJSON.getString("uri");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return uri;
    }

    public static int deleteVideo_URL(String videoName){
        int statusCode;

        statusCode = doSimpleGet(Config.DELETE_VIDEO_URL + "?videoname=" + videoName);
        return statusCode;
    }

    public static int upload(String videoName){
        int statusCode;

        statusCode = doSimpleGet(Config.UPLOAD_VIDEO_URL + "?videoname=" + videoName);
        return statusCode;
    }

    public static int changeVisibility_URL(String videoName, String changeType){
        int statusCode;

        statusCode = doSimpleGet(Config.VIDEO_VISIBILITY_URL + "?change-visibility=" + videoName + "&change-type=" + changeType);
        return statusCode;
    }

    public static void uploadFinished(String videoname){

        doSimpleGet(Config.UPLOAD_FINISHED + videoname);
    }

    public static JSONObject getList(String type) {
        JSONObject jsonResponse;
        jsonResponse = doJSONGet(Config.BASE_URL + type);

        return jsonResponse;
    }

    private static JSONObject doJSONGet(String urlAddress) {

        try {
            URL obj = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            //con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            JSONObject jsonResponse = new JSONObject(response.toString());
            con.disconnect();

            if (responseCode == 200) {
                return jsonResponse;
            } else {
                return null;
            }
        }catch(Exception e) {
            return null;
        }

    }

    private static Integer doSimpleGet(String urlAddress){

        try{
        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/plain");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();

        int statusCode = connection.getResponseCode();
        connection.disconnect();
        return statusCode;

        }catch(Exception e) {
            return null;
        }

    }

    public static void showAlert(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle("Response from Server")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private static String generateHashPassword(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);

        return generatedPassword;
    }

};





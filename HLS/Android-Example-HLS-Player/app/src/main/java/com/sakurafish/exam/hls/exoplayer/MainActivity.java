package com.sakurafish.exam.hls.exoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void onSampleSelected(String uri) {
        Intent mpdIntent = new Intent(this, PlayerActivity.class)
                .setData(Uri.parse(uri));
        startActivity(mpdIntent);
    }

    public void onClick1(View view) {
        onSampleSelected("http://10.0.2.2:8081/video/index.m3u8");
    }

}

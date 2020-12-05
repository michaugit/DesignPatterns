package com.videoapp.AppActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.videoapp.Fragments.CameraFragment;
import com.videoapp.Fragments.UserFragment;
import com.videoapp.Fragments.StoryFragment;
import com.videoapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

        final View background = findViewById(R.id.viewPagerBackground);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    background.setBackgroundColor(getResources().getColor(R.color.greenAGH));
                    background.setAlpha(1 - positionOffset);
                } else if (position == 1) {
                    background.setBackgroundColor(getResources().getColor(R.color.redAGH));
                    background.setAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // if the result is capturing Image
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//
//                // successfully captured the image
//                // launching upload activity
//                launchUploadActivity(true);
//
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//                // user cancelled Image capture
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();
//
//            } else {
//                // failed to capture image
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
//            }
//
//        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//
//                // video successfully recorded
//                // launching upload activity
//                launchUploadActivity(false);
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//                // user cancelled recording
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled video recording", Toast.LENGTH_SHORT)
//                        .show();
//
//            } else {
//                // failed to record video
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }

    private class MainPagerAdapter extends FragmentPagerAdapter {
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return UserFragment.newInstance();
                case 1:
                    return CameraFragment.newInstance();
                case 2:
                    return StoryFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}

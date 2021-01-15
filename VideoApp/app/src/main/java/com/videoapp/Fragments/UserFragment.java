package com.videoapp.Fragments;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.videoapp.Adapters.UserAdapter;
import com.videoapp.AppActivity.PlayerActivity;
import com.videoapp.ServerConnector;
import com.videoapp.Upload.Config;
import com.videoapp.Video;
import com.videoapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class UserFragment extends Fragment implements UserAdapter.ListItemClickListener {

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    private UserAdapter adapter;
    private RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        recycler = view.findViewById(R.id.userRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);

        adapter = new UserAdapter(fetchMoviesFromServerSimulator(), this);
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Video videoClicked = adapter.getVideo(clickedItemIndex);
        playVideo(videoClicked);
    }

    @Override
    public void onListItemLongClick(int clickedItemIndex, View anchor) {
        Video videoLongClicked= adapter.getVideo(clickedItemIndex);
        openPopupMenu(anchor, videoLongClicked);
    }

    @SuppressLint("ResourceType")
    public void openPopupMenu(View anchor, Video videoClicked){

        PopupMenu pm = new PopupMenu(getContext(), anchor);
        pm.getMenuInflater().inflate(R.layout.popup_menu, pm.getMenu());

        MenuItem visibility= pm.getMenu().findItem(R.id.visibility);
        if(videoClicked.visible){
            visibility.setTitle("Make Video Invisible");
        }
        else{
            visibility.setTitle("Make Video Visible");
        }

        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.visibility:
                        changeVisibility(videoClicked);


                    case R.id.deletion:
                        deleteVideo(videoClicked);

                }

                return true;
            }
        });
        pm.show();
    }

    public void playVideo(Video video){
        String uri = null;
        uri = ServerConnector.playVideo_URL(video.name);

        if(uri != null){
            Intent mpdIntent = new Intent(getContext(),PlayerActivity.class)
                    .setData(Uri.parse(uri));
            startActivity(mpdIntent);
        }
        else{
            ServerConnector.showAlert("Something went wrong. You cannot watch this video.", this.getContext());
        }

    }

    public void deleteVideo(Video video){
        int statusCode = ServerConnector.deleteVideo_URL(video.name);
        if(ServerConnector.deleteVideo_URL(video.name) == 200){
            adapter.deleteItem(video);
            ServerConnector.showAlert("Video has been deleted.", this.getContext());
        }else if(statusCode == 404){
                ServerConnector.showAlert("Video not found.", this.getContext());
        }else if(statusCode == 511){
            ServerConnector.showAlert("Session has expired.", this.getContext());
        }

    }

    public void changeVisibility(Video video){
        int statusCode = ServerConnector.changeVisibility_URL(video.name, video.checkType());
        if(statusCode == 200){
            video.visible = !video.visible;
            ServerConnector.showAlert("Video status changed to "+video.checkType(), this.getContext());
        }else if(statusCode == 404){
            ServerConnector.showAlert("Video not found", this.getContext());
        }else if(statusCode == 406){
            ServerConnector.showAlert("Not acceptable. Status is already "+video.checkType(), this.getContext());
        }else if(statusCode == 511){
            ServerConnector.showAlert("Session has expired", this.getContext());
        }

    }

    public ArrayList<Video> fetchMoviesFromServerSimulator(){
        ArrayList<Video> dataList =  new ArrayList<>();
        //TODO get from json
//        JSONObject jsonResponse = ServerConnector.getList("user-videos");
//        JSONArray arr = null;
//        try {
//            arr = jsonResponse.getJSONArray("videonames");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < arr.length(); i++){
//            try {
//                dataList.add(new Video(arr.getJSONObject(i).getString("videoname")));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        dataList.add(new Video("Filmnr1"));
        dataList.add(new Video("Film nr 2"));
        dataList.add(new Video("Film nr 3"));
        dataList.add(new Video("Film nr 4"));
        dataList.add(new Video("Film nr 5"));
        dataList.add(new Video("Film nr 6"));
        dataList.add(new Video("Film nr 7"));
        dataList.add(new Video("Film nr 8"));
        dataList.add(new Video("Film nr 9"));
        dataList.add(new Video("Film nr 10"));
        dataList.add(new Video("Film nr 11"));
        dataList.add(new Video("Film nr 12"));
        dataList.add(new Video("Film nr 13"));

        return  dataList;
    }
}

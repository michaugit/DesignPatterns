package com.videoapp.Fragments;


import android.annotation.SuppressLint;
import android.content.ClipData;
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
import com.videoapp.ServerConnector;
import com.videoapp.Video;
import com.videoapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class UserFragment extends Fragment implements UserAdapter.ListItemClickListener {

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    private UserAdapter adapter;
    private RecyclerView recycler;
    private Toast toast;

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
                        try {
                            changeVisibility(videoClicked);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.deletion:

                        try {
                            deleteVideo(videoClicked);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return true;
                }

                return true;
            }
        });
        pm.show();
    }

    public void playVideo(Video video){
//        if (toast != null) {
//            toast.cancel();
//        }
        int statusCode = ServerConnector.playVideo(video.name);
        if(statusCode == 200){
            //TODO implement hls streaming!
        }if(statusCode == 404){
            ServerConnector.showAlert("Video not found", this.getContext());
        }if(statusCode == 511){
            ServerConnector.showAlert("Session has expired.", this.getContext());
        }
//        String message = "On Click play on " + video.name;
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void deleteVideo(Video video) throws IOException {
        int statusCode = ServerConnector.deleteVideo_URL(video.name);
        if(ServerConnector.deleteVideo_URL(video.name) == 200){
            adapter.deleteItem(video);
            ServerConnector.showAlert("Video has been deleted.", this.getContext());
        }if(statusCode == 404){
            ServerConnector.showAlert("Video not found.", this.getContext());
        }if(statusCode == 511){
            ServerConnector.showAlert("Session has expired.", this.getContext());
        }

    }

    public void changeVisibility(Video video) throws IOException {
        int statusCode = ServerConnector.changeVisibility_URL(video.name, video.checkType());
        if(statusCode == 200){
            video.visible = !video.visible;
            ServerConnector.showAlert("Video status changed to "+video.checkType(), this.getContext());
        }if(statusCode == 404){
            ServerConnector.showAlert("Video not found", this.getContext());
        }if(statusCode == 406){
            ServerConnector.showAlert("Not acceptable. Status is already "+video.checkType(), this.getContext());
        }if(statusCode == 511){
            ServerConnector.showAlert("Session has expired", this.getContext());
        }

    }

    public ArrayList<Video> fetchMoviesFromServerSimulator(){
        //JSONObject jsonResponse = ServerConnector.getList("user-videos");
        //TODO get from json
        ArrayList<Video> dataList =  new ArrayList<>();
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

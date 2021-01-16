package com.videoapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.videoapp.Adapters.StoryAdapter;
import com.videoapp.Adapters.UserAdapter;
import com.videoapp.AppActivity.PlayerActivity;
import com.videoapp.R;
import com.videoapp.ServerConnector;
import com.videoapp.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoryFragment extends Fragment implements StoryAdapter.ListItemClickListener  {

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }

    private StoryAdapter adapter;
    private RecyclerView recycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story, container, false);

        recycler = view.findViewById(R.id.storyRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        adapter = new StoryAdapter(fetchMoviesFromServer(), this);
        recycler.setAdapter(adapter);

        return view;
    }

    public void onUpdateView() {
        if(adapter != null) {
            System.out.println("XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            adapter.updateData(fetchMoviesFromServer());
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Video clickedVideo = adapter.getVideo(clickedItemIndex);
        playVideo(clickedVideo);
    }

    private void playVideo(Video video){
        String uri = null;
        uri = ServerConnector.playVideo_URL(video.videoName);

        if(uri != null){
            Intent mpdIntent = new Intent(getContext(),PlayerActivity.class)
                    .setData(Uri.parse(uri));
            startActivity(mpdIntent);
        }
        else{
            ServerConnector.showAlert("Something went wrong. You cannot watch this video.", this.getContext());
        }
    }

    public ArrayList<Video> fetchMoviesFromServer(){
        ArrayList<Video> dataList =  new ArrayList<>();
        JSONObject jsonObj = null;
//        TODO all-videos
        JSONArray jsonResponse = ServerConnector.getList("all-videos");

        for (int i=0;i< jsonResponse.length();i++){
            try {
                jsonObj = (JSONObject) jsonResponse.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                dataList.add(new Video(jsonObj.get("username").toString(), jsonObj.get("videoname").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return  dataList;
    }
}

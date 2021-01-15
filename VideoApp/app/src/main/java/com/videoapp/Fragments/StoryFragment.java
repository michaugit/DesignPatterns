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
import android.widget.Toast;

import com.videoapp.Adapters.StoryAdapter;
import com.videoapp.Adapters.UserAdapter;
import com.videoapp.AppActivity.PlayerActivity;
import com.videoapp.R;
import com.videoapp.ServerConnector;
import com.videoapp.Upload.Config;
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
   // private Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story, container, false);

        recycler = view.findViewById(R.id.storyRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        adapter = new StoryAdapter(fetchMoviesFromServerSimulator(), this);
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Video clickedVideo = adapter.getVideo(clickedItemIndex);
        playVideo(clickedVideo);
    }

    private void playVideo(Video video){
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

    public ArrayList<Video> fetchMoviesFromServerSimulator(){
        ArrayList<Video> dataList =  new ArrayList<>();
        //TODO get from json
//        JSONObject jsonResponse = ServerConnector.getList("story-videos");
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

        dataList.add(new Video("STORY Film nr 1"));
        dataList.add(new Video("STORY Film nr 2"));
        dataList.add(new Video("STORY Film nr 3"));
        dataList.add(new Video("STORY Film nr 4"));
        dataList.add(new Video("STORY Film nr 5"));
        dataList.add(new Video("STORY Film nr 6"));
        dataList.add(new Video("STORY Film nr 7"));
        dataList.add(new Video("STORY Film nr 8"));
        dataList.add(new Video("STORY Film nr 9"));
        dataList.add(new Video("STORY Film nr 10"));
        dataList.add(new Video("STORY Film nr 11"));
        dataList.add(new Video("STORY Film nr 12"));
        dataList.add(new Video("STORY Film nr 13"));

        return  dataList;
    }
}

package com.videoapp.Fragments;


import android.annotation.SuppressLint;
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

import com.videoapp.Adapters.UserAdapter;
import com.videoapp.AppActivity.PlayerActivity;
import com.videoapp.ServerConnector;
import com.videoapp.Video;
import com.videoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        adapter = new UserAdapter(fetchMoviesFromServer(), this, getContext());
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            adapter.updateData(fetchMoviesFromServer());
        }
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Video videoClicked = adapter.getVideo(clickedItemIndex);
        playVideo(videoClicked);
    }

    @Override
    public void onListItemLongClick(int clickedItemIndex, View anchor) {
        Video videoLongClicked = adapter.getVideo(clickedItemIndex);
        openPopupMenu(anchor, videoLongClicked);
    }

    @SuppressLint("ResourceType")
    public void openPopupMenu(View anchor, Video videoClicked) {

        PopupMenu pm = new PopupMenu(getContext(), anchor);
        pm.getMenuInflater().inflate(R.layout.popup_menu, pm.getMenu());

        MenuItem visibility = pm.getMenu().findItem(R.id.visibility);
        if (videoClicked.getVisibility() == Video.Visibility.PUBLIC) {
            visibility.setTitle("Make Video Invisible");
        } else {
            visibility.setTitle("Make Video Visible");
        }

        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.visibility:
                        changeVisibility(videoClicked);
                        return true;

                    case R.id.deletion:
                        deleteVideo(videoClicked);
                        return true;
                }
                return true;
            }
        });
        pm.show();
    }

    public void playVideo(Video video) {
        String uri = null;
        uri = ServerConnector.playVideo_URL(video.videoName);

        if (uri != null) {
            Intent mpdIntent = new Intent(getContext(), PlayerActivity.class)
                    .setData(Uri.parse(uri));
            startActivity(mpdIntent);
        } else {
            ServerConnector.showAlert("Something went wrong. You cannot watch this video.", this.getContext());
        }

    }

    public void deleteVideo(Video video) {
        int statusCode = ServerConnector.deleteVideo_URL(video.videoName);
        if (statusCode == 200) {
            adapter.deleteItem(video);
            ServerConnector.showAlert("Video has been deleted.", this.getContext());
        } else if (statusCode == 404) {
            ServerConnector.showAlert("Video not found.", this.getContext());
        } else if (statusCode == 511) {
            ServerConnector.showAlert("Session has expired.", this.getContext());
        }

    }

    public void changeVisibility(Video video) {
        int statusCode = ServerConnector.changeVisibility_URL(video.videoName, video.getTypeChanged());
        if (statusCode == 200) {
            video.changeVisibility();
            adapter.notifyDataSetChanged();
            ServerConnector.showAlert("Video status changed to " + video.checkType() + ".", this.getContext());
        } else if (statusCode == 404) {
            ServerConnector.showAlert("Video not found.", this.getContext());
        } else if (statusCode == 406) {
            ServerConnector.showAlert("Not acceptable. Status is already " + video.checkType() + ".", this.getContext());
        } else if (statusCode == 511) {
            ServerConnector.showAlert("Session has expired.", this.getContext());
        }

    }

    public ArrayList<Video> fetchMoviesFromServer() {
        ArrayList<Video> dataList = new ArrayList<>();
        JSONObject jsonObj = null;
        JSONArray jsonResponse = ServerConnector.getList("user-videos");
        if (jsonResponse != null) {
            for (int i = 0; i < jsonResponse.length(); i++) {
                try {
                    jsonObj = (JSONObject) jsonResponse.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String videoUserName = jsonObj.get("username").toString();
                    String videoName = jsonObj.get("videoname").toString();
                    Video.Visibility videoVisibility;

                    if (jsonObj.get("visibility").equals("public")) {
                        videoVisibility = Video.Visibility.PUBLIC;
                    } else {
                        videoVisibility = Video.Visibility.PRIVATE;
                    }

                    dataList.add(Video.builder().userName(videoUserName).videoName(videoName).visibility(videoVisibility).build());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        return dataList;
    }
}

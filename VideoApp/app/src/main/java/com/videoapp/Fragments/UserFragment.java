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
import com.videoapp.Video;
import com.videoapp.R;

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

    public void playVideo(Video video){
        if (toast != null) {
            toast.cancel();
        }
        String message = "On Click play on " + video.name;
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void deleteVideo(Video video){
        if (toast != null) {
            toast.cancel();
        }
        adapter.deleteItem(video);
        Toast.makeText(getActivity(), "Clicked delete on" + video.name, Toast.LENGTH_SHORT).show();
    }

    public void changeVisibility(Video video){
        if (toast != null) {
            toast.cancel();
        }
        video.visible = !video.visible;
        Toast.makeText(getActivity(), "Clicked change visiblity on" + video.name, Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Video> fetchMoviesFromServerSimulator(){
        ArrayList<Video> dataList =  new ArrayList<>();
        dataList.add(new Video("Film nr 1"));
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

package com.videoapp.Fragments;

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
import com.videoapp.R;

public class StoryFragment extends Fragment implements StoryAdapter.ListItemClickListener  {

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }

    private StoryAdapter adapter;
    private RecyclerView recycler;
    private Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_story, container, false);

        recycler = view.findViewById(R.id.storyRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);

        adapter = new StoryAdapter(25, this);
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), Integer.toString(clickedItemIndex), Toast.LENGTH_SHORT);
        toast.show();
    }
}

package com.videoapp.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.videoapp.Adapters.UserAdapter;
import com.videoapp.R;

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

        adapter = new UserAdapter(25, this);
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

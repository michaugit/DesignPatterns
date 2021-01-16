package com.videoapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.videoapp.R;
import com.videoapp.Video;

import java.util.ArrayList;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ChatViewHolder> {

    private ArrayList<Video> dataList;
    final private ListItemClickListener mOnClickListener;

    public StoryAdapter(ArrayList<Video> dataList, ListItemClickListener clickListener) {
        this.dataList = dataList;
        mOnClickListener = clickListener;
    }

    public void updateData(ArrayList<Video> newData) {
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }

    public Video getVideo(int positionIndex) {
        return dataList.get(positionIndex);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_story, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        String nameOfVideo = dataList.get(position).videoName;
        holder.videoName.setText(nameOfVideo);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView videoName;

        public ChatViewHolder(View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.video_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}

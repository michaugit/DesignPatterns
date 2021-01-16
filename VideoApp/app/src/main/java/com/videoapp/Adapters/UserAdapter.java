package com.videoapp.Adapters;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.videoapp.Video;
import com.videoapp.R;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Video> dataList;
    final private ListItemClickListener mOnClickListener;

    public UserAdapter(ArrayList<Video> dataList, ListItemClickListener clickListener, Context context) {
        this.context = context;
        this.dataList = dataList;
        mOnClickListener = clickListener;
    }

    public void addItem(Video newItem) {
        dataList.add(newItem);
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Video> newData) {
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void deleteItem(Video Item) {
        dataList.remove(Item);
        notifyDataSetChanged();
    }

    public Video getVideo(int positionIndex) {
        return dataList.get(positionIndex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nameOfVideo = dataList.get(position).videoName;
        holder.videoName.setText(nameOfVideo);
        if (dataList.get(position).getVisibility() == Video.Visibility.PUBLIC){
            holder.videoVisibility.setText("PUBLIC");
            holder.videoVisibility.setTextColor(ContextCompat.getColor(context, R.color.greenAGH));
        }else{
                holder.videoVisibility.setText("PRIVATE");
                holder.videoVisibility.setTextColor(ContextCompat.getColor(context, R.color.redAGH));
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);

        void onListItemLongClick(int clickedItemIndex, View anchor);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView videoName;
        TextView videoVisibility;

        public ViewHolder(View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.video_name);
            videoVisibility = itemView.findViewById(R.id.video_visiblity);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        @Override
        public boolean onLongClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemLongClick(clickedPosition, v);
            return true;
        }
    }
}

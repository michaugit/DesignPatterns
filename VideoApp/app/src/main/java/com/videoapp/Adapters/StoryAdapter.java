package com.videoapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.videoapp.R;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ChatViewHolder>{

    private int nbItems;
    final private ListItemClickListener mOnClickListener;

    public StoryAdapter(int nbItems, ListItemClickListener clickListener){
        this.nbItems = nbItems;
        mOnClickListener = clickListener;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_story, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return nbItems;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View typeChat;
        TextView name;
        TextView date;

        public ChatViewHolder(View itemView) {
            super(itemView);
//            name = itemView.findViewById(R.id.snap_name);
//            date = itemView.findViewById(R.id.snap_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}

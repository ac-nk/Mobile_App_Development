package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    private List<Song> mSongsList;

    public SongsAdapter(List<Song> songsList) {
        this.mSongsList = songsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTitle, mTextViewArtist, mTextViewYear;

        public MyViewHolder(View view) {
            super(view);
            mTextViewTitle = view.findViewById(R.id.title);
            mTextViewArtist = view.findViewById(R.id.artist);
            mTextViewYear = view.findViewById(R.id.year);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Song song = mSongsList.get(position);
        holder.mTextViewTitle.setText(song.getTitle());
        holder.mTextViewArtist.setText(song.getArtist());
        holder.mTextViewYear.setText(Integer.toString(song.getYear()));
    }
    @Override
    public int getItemCount() {
        return mSongsList.size();
    }
}

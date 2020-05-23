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

//        if(position < mSongsList.size()) {
            Song song = mSongsList.get(position);
//            if(song != null){
//                if(song.title != null)
                    holder.mTextViewTitle.setText(song.title);
//                if(song.artist != null)
                    holder.mTextViewArtist.setText(song.artist);
//                if(Integer.toString(song.year) != null)
                    holder.mTextViewYear.setText(Integer.toString(song.year));
//            }
//        }
    }
    @Override
    public int getItemCount() {
        return mSongsList.size();
    }
}

package com.example.recyclerview;

//import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    private List<Song> mSongsList;
    private AdapterCallback mAdapterCallback;
    private SongVotes mSongVotes;

    public SongsAdapter(List<Song> songsList, SongVotes svs, Context context) {
        this.mSongsList = songsList;
        this.mSongVotes = svs;
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextViewTitle, mTextViewArtist, mTextViewYear, mTextViewVote;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextViewTitle = view.findViewById(R.id.title);
            mTextViewArtist = view.findViewById(R.id.artist);
            mTextViewYear = view.findViewById(R.id.year);
            mTextViewVote = view.findViewById(R.id.vote);
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            mAdapterCallback.onAdapterCallback(itemPosition);
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
            SongVote tempSongVote = mSongVotes.getSong(position);
            Song song = mSongsList.get(position);

            holder.mTextViewVote.setText("VOTES: " + tempSongVote.getVoteNum());
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
    public void updateSongVotes (SongVotes svs) {
        mSongVotes = svs;
    }
    public static interface AdapterCallback {
        void onAdapterCallback(int pos);
    }
}

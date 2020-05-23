package com.example.recyclerview;

public class SongVotes {
    private SongVote[] songVotes;

    public SongVotes() {

    }
    public SongVotes(int songNum){
        songVotes = new SongVote[songNum];
        for(int x= 0; x < songNum; x++) {
            songVotes[x] = new SongVote(x, 0);
        }
    }
    public void setSongVotes(int voteNum, int position) {
        this.songVotes[position] = new SongVote(position, voteNum);
    }
    public SongVote getSong(int position) {
        return songVotes[position];
    }
}

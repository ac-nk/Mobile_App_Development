package com.example.recyclerview;

public class SongVote {
    private int position;
    private int voteNum;

    public SongVote(){

    }
    public SongVote(int p, int v) {
        this.position = p;
        this.voteNum = v;
    }
    public void setVoteNum(int n) {
        this.voteNum = n;
    }

    public int getVoteNum() {
        return this.voteNum;
    }

    public int getPosition() {
        return position;
    }
}

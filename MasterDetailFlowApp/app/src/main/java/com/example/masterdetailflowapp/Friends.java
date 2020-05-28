package com.example.masterdetailflowapp;

public class Friends {
    private String topName;
    private String middleName;
    private String bottomName;

    public Friends(){
        this.topName = "add a name!";
        this.middleName = "add a name!";
        this.bottomName = "add a name!";
    }
    public Friends(String t, String m, String b){
        this.topName = t;
        this.middleName = m;
        this.bottomName = b;
    }

    public void setName(int pos, String n) {
        switch(pos){
            case 0:
                this.topName = n;
                break;
            case 1:
                this.middleName = n;
                break;
            case 2:
                this.bottomName = n;
                break;
            default:
                break;
        }
    }

    public String[] getAllNames(){
        String[] temp = {this.topName, this.middleName, this.bottomName};
        return temp;
    }

    public String getName(int pos) {
        switch(pos){
            case 0:
                return this.topName;
            case 1:
                return this.middleName;
            case 2:
                return this.bottomName;
            default:
                return "number needs to be between 0 to 2";
        }
    }
}

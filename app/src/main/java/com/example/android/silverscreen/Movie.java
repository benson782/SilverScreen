package com.example.android.silverscreen;

public class Movie {

    private String mId = null;
    private String mTitle = null;
    private String mPosterPath = null;

    public Movie(String id, String title, String posterPath){
        mId = id;
        mTitle = title;
        mPosterPath = posterPath;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getPosterPath() {
        return mPosterPath;
    }



}

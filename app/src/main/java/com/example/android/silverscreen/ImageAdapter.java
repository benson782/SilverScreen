package com.example.android.silverscreen;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends ArrayAdapter<Movie> {

    private final String LOG_TAG = ImageAdapter.class.getSimpleName();

    public ImageAdapter(Context context, ArrayList<Movie> movie) {
        super(context, 0, movie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        // Use Picasso to fetch the image and set in the ImageView
        String posterPath = getItem(position).getPosterPath();
        String url = getContext().getString(R.string.url_prefix_movie_poster) + posterPath;
        Log.d(LOG_TAG, "Movie Poster URL: " + url);
        Picasso.with(getContext())
                .load(url)
                .into(imageView);

        return imageView;
    }

}

package com.example.android.silverscreen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends ArrayAdapter<String>{

    private final String LOG_TAG = ImageAdapter.class.getSimpleName();

    public ImageAdapter(Context context, ArrayList<String> str) {
        super(context, 0, str);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(getContext());
        } else {
            imageView = (ImageView) convertView;
        }

        // Use Picasso to set the image
        String url = "http://image.tmdb.org/t/p/w185" + getItem(position);
        Picasso.with(getContext()).load(url).into(imageView);

        return imageView;
    }

}

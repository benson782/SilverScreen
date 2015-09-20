package com.example.android.silverscreen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends ArrayAdapter<String> {

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
            imageView.setLayoutParams(new GridView.LayoutParams(185 * 2, 277 * 2));
        } else {
            imageView = (ImageView) convertView;
        }

        // Use Picasso to fetch the image and set in the ImageView
        String url = "http://image.tmdb.org/t/p/w185" + getItem(position);
        Picasso.with(getContext()).load(url).fit().centerCrop().into(imageView);

        return imageView;
    }

}

package com.example.android.silverscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mMovieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the ListView.  Here's a sample weekly forecast
        String[] data = {
                "Movie 1", "Movie 2", "Movie 3", "Movie 4", "Movie 5",
                "Movie 6", "Movie 7", "Movie 8", "Movie 9", "Movie 10",
                "Movie 11", "Movie 12", "Movie 13", "Movie 14", "Movie 15",
                "Movie 16", "Movie 17", "Movie 18", "Movie 19", "Movie 20",

        };
        List<String> dummyData = new ArrayList<>(Arrays.asList(data));

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mMovieAdapter = new ArrayAdapter<>(
                getActivity(), // The current context (this activity)
                R.layout.grid_item_movie, // The name of the layout ID.
                R.id.grid_item_movie_textview, // The ID of the textview to populate.
                dummyData
                /*new ArrayList<String>()*/);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(mMovieAdapter);
/*        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = mForecastAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });
*/

        return rootView;
    }
}

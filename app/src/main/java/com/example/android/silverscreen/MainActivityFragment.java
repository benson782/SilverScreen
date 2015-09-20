package com.example.android.silverscreen;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = mMovieAdapter.getItem(position);
                Toast.makeText(getActivity(), movie, Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);*/
            }
        });

        updateMovies();

        return rootView;
    }

    private void updateMovies() {
        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {
                // Construct the URL for the The Movies DB query
                // Possible parameters are available at TMDb's API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" +
                        getString(R.string.my_api_key));

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    //forecastJsonStr = null;
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    //forecastJsonStr = null;
                    return null;
                }
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie data: " + movieJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            // Parse the JSON movie data
            try {
                String[] movieList = getMovieList(movieJsonStr);
                for (int i = 0; i < movieList.length; i++) {
                    Log.v(LOG_TAG, "Movie " + i + ": " + movieList[i]);
                }
                return movieList;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mMovieAdapter.clear();
                for (String movieTitleStr : result) {
                    mMovieAdapter.add(movieTitleStr);
                }
                // New data is back from the server.  Hooray!
            }
        }

        private String[] getMovieList(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            //final String TMDB_POSTER_PATH = "poster_path";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(TMDB_RESULTS);

            String[] resultList = new String[movieArray.length()];
            for (int i = 0; i < movieArray.length(); i++) {
                String movieOriginalTitle = movieArray.getJSONObject(i).getString(TMDB_ORIGINAL_TITLE);
                resultList[i] = movieOriginalTitle;
            }

            return resultList;
        }
    }
}

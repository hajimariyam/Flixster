package com.codepath.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.flixster.adapters.MovieAdapter;
import com.codepath.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // save url for get request (to retrieve currently playing movies) as constant
    // api key is embedded in url, don't need to pass as parameter in request
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // tag to log data below
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define recycler view
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        // instantiate movies, to be modified after API response
        movies = new ArrayList<>();

        // Last step after basic version of adapter is working:
        // bind the adapter to the data source to populate recycle view

        // Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager on the recycler view so RV knows how to layout the different views on screen
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        // create an instance of AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();

        // make get request on url above, using json since API does
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            // callback if network request is successful
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                // (check url!) data is returned as json object "results", which contains list of movies playing
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    // log results under "MainActivity" tag
                    Log.i(TAG, "Results: " + results.toString());
                    // modify the "movies" that the adapter has access to instead of changing the reference altogether
                    // call static method defined in Movie.java that returns list of movie objects, pass in results
                    movies.addAll(Movie.fromJsonArray(results));
                    // notify adapter when data changes, so to re-render recycler view
                    movieAdapter.notifyDataSetChanged();
                    // log value of movies to check and debug
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    // log error if exception encountered
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            // callback if network request fails
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}
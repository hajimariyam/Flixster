package com.codepath.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    int movieId;
    String posterPath;
    String title;
    String overview;
    double rating;

    // empty constructor needed by the Parceler library
    public Movie() {}

    // create constructor to take in json object and re-out relevant fields
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    // use static method to take in json array & turn into list of movies
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        // iterate through json array and constructing a movie for each element
        List<Movie> movies = new ArrayList<>();
        // for loop until the length of the array
        for (int i = 0; i < movieJsonArray.length(); i++) {
            // add movie at each position of the array
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        // list of movie objects
        return movies;
    }

    // use AndroidStudio's Generate feature to create getters to get data out of the movie objects

    // create full url to pass into image loading library (Glide) to render image
    // hardcoded here, should make api request to fetch possible sizes & append size and relative path to base url
    // size = width of 342, %s adds passed in relative path
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }
}

package com.codepath.flixster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.flixster.R;
import com.codepath.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

// define adapter, which extends base recycle view adapter, parameterized by view holder defined below
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // use to fill out onCreateViewHolder, onBindViewHolder, getItemCount,
    // needed when extending the abstract recycler view adapter
    Context context; // where the adapter is being constructed from
    List<Movie> movies; // data

    // constructor used to pass in member variables above for adapater to hold on to
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // inflate XML layout (item_movie.xml) and return it inside a view holder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // log to show that only enough views are inflated to fit the page, then new data is binded
        Log.d("MovieAdapter", "onCreateViewHolder");
        // static method to take in context, inflate item_movie.xml, and return view
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // wrap view inside a view holder
        return new ViewHolder(movieView);
    }

    // at said position, populate data into the view in the view holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // log to show that new data is binded per row, a new view is not created every time
        // tag is to filter logcat messages
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // get the movie at the passed in position
        Movie movie = movies.get(position);
        // bind (populate) the movie data into the view holder below
        holder.bind(movie);
    }

    // return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // define inner view holder class that extends base recycler view holder
    // view holder represents row designed in item_movie.xml
    public class ViewHolder extends RecyclerView.ViewHolder {

        // define member variables for each view in view holder to bind data appropriately
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        // constructor, pass in view holder
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            // define where each view is coming from
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        // bind movie data into view holder by using getters to populate each view
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // use Glide library to render images; load url created before, go into certain view
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }
    }
}

package com.gosemathraj.popularmoviesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.activities.MovieDetailsActivity;
import com.gosemathraj.popularmoviesapp.adapters.RecyclerViewReviewAdapter;
import com.gosemathraj.popularmoviesapp.adapters.RecyclerViewTrailerAdapter;
import com.gosemathraj.popularmoviesapp.models.Movie;
import com.gosemathraj.popularmoviesapp.models.MovieReviews;
import com.gosemathraj.popularmoviesapp.models.MovieReviewsAll;
import com.gosemathraj.popularmoviesapp.models.MovieTrailers;
import com.gosemathraj.popularmoviesapp.models.MovieTrailersAll;
import com.gosemathraj.popularmoviesapp.utils.MovieApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iamsparsh on 8/5/16.
 */
public class Fragment_Movie_Details extends Fragment {

    private Movie m;
    private MovieTrailersAll mtrailersAll;
    private MovieReviewsAll movieReviewsAll;
    private List<MovieTrailers> mtrailers;
    private List<MovieReviews> movieReviews;
    private TextView title;
    private TextView ratings;
    private TextView average;
    private ImageView poster;
    private ImageView bg_poster;
    private RecyclerView trailers;
    private RecyclerView reviews;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saInstanceState){

        View view = inflater.inflate(R.layout.fragment_movie_details,container,false);

        MovieDetailsActivity activity;
        activity = (MovieDetailsActivity)getActivity();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m = activity.returnMovie();

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(m.getTitle());

        title = (TextView) view.findViewById(R.id.detail_title);
        ratings = (TextView)view.findViewById(R.id.detail_ratings);
        average = (TextView) view.findViewById(R.id.detail_average);
        poster = (ImageView) view.findViewById(R.id.detail_poster);
        bg_poster = (ImageView) view.findViewById(R.id.backdrop);
        trailers = (RecyclerView) view.findViewById(R.id.recyclerview_trailers);
        reviews = (RecyclerView) view.findViewById(R.id.recyclerview_reviews);

        title.setText(m.getTitle());
        ratings.setText(m.getPopularity());
        average.setText(m.getVote_average());

        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500"+m.getBackdrop_path()).into(bg_poster);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185" + m.getPoster_path()).into(poster);

        getTrailerInfo();
        getReviewInfo();
        return view;
    }

    private void getReviewInfo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieReviewsAll> call = movieApi.loadMovieReviews(m.getId());

        call.enqueue(new Callback<MovieReviewsAll>() {
            @Override
            public void onResponse(Call<MovieReviewsAll> call, Response<MovieReviewsAll> response) {

                movieReviewsAll = response.body();
                movieReviews = movieReviewsAll.getResults();

                reviews.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                reviews.setHasFixedSize(true);
                reviews.setAdapter(new RecyclerViewReviewAdapter(getContext(),movieReviews));

            }

            @Override
            public void onFailure(Call<MovieReviewsAll> call, Throwable t) {

            }
        });

    }

    private void getTrailerInfo(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieTrailersAll> call = movieApi.loadMovieTrailers(m.getId());

        call.enqueue(new Callback<MovieTrailersAll>() {
            @Override
            public void onResponse(Call<MovieTrailersAll> call, Response<MovieTrailersAll> response) {

                mtrailersAll = response.body();
                mtrailers = mtrailersAll.getResults();

                trailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                trailers.setHasFixedSize(true);


                trailers.setNestedScrollingEnabled(false);
                trailers.setAdapter(new RecyclerViewTrailerAdapter(getContext(),mtrailers));

            }

            @Override
            public void onFailure(Call<MovieTrailersAll> call, Throwable t) {

            }
        });
    }

}

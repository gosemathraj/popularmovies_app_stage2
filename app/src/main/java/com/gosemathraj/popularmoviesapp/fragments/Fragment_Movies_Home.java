package com.gosemathraj.popularmoviesapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.adapters.RecyclerViewCustomAdapter;
import com.gosemathraj.popularmoviesapp.adapters.ViewPaderAdapter;
import com.gosemathraj.popularmoviesapp.models.Movie;
import com.gosemathraj.popularmoviesapp.models.MovieResult;
import com.gosemathraj.popularmoviesapp.models.MovieTrailers;
import com.gosemathraj.popularmoviesapp.models.MovieTrailersAll;
import com.gosemathraj.popularmoviesapp.utils.MovieApi;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by iamsparsh on 4/5/16.
 */
public class Fragment_Movies_Home extends Fragment {

    private MovieTrailersAll mtrailersAll;
    private List<MovieTrailers> mtrailers;
    private MovieResult movieResult;
    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;
    private String[] image_resources;
    private String[] top_titles = {"English","Hindi","Popular","Latest","Retro","Top 10"};



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_movies_home,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        getMoviesList();

        return view;
    }

    private void setViewPagerData() {

        image_resources = new String[5];
        for(int i = 0;i < 5;i++){

            Movie m = movieList.get(i);
            image_resources[i] = m.getPoster_path();
        }
    }

    private void getMoviesList() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                movieList = movieResult.getResult();
                setViewPagerData();
                recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(movieList, getContext(), top_titles, image_resources);
                recyclerView.setAdapter(recyclerViewCustomAdapter);
                recyclerViewCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }
}

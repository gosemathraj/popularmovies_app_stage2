package com.gosemathraj.popularmoviesapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;
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

    private ArrayList<Movie> popularMovies = new ArrayList<>();
    private ArrayList<Movie> highestRateMovies = new ArrayList<>();
    private ArrayList<Movie> upcomingMovies = new ArrayList<>();
    private ArrayList<Movie> highestGrossingMovies = new ArrayList<>();
    private ArrayList<Movie> thisYearReleaseMovies = new ArrayList<>();
    private ArrayList<Movie> movieList = new ArrayList<>();
    private MovieResult movieResult;

    private static boolean loaded;

    private ArrayList<List<Movie>> allMovies = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;
    private String[] image_resources;
    private String[] top_titles = {"Popular","Highest rated","Upcoming","Highest Grossing","latest release","similar"};



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_movies_home,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));



        if(saveInstanceState == null) {
            if(loaded == false) {
                getPopularMovies();
            }else{
                allMovies.add(popularMovies);
                allMovies.add(highestRateMovies);
                allMovies.add(upcomingMovies);
                allMovies.add(highestGrossingMovies);
                allMovies.add(thisYearReleaseMovies);
                allMovies.add(movieList);

                String s2 = "hello";
                setsavedInstanceLayout();
            }
        }else if(loaded == true){
            allMovies.add(popularMovies);
            allMovies.add(highestRateMovies);
            allMovies.add(upcomingMovies);
            allMovies.add(highestGrossingMovies);
            allMovies.add(thisYearReleaseMovies);
            allMovies.add(movieList);

            String s2 = "hello";
            setsavedInstanceLayout();
        }else{
            getPopularMovies();
        }
        return view;
    }

    private boolean checkDataState() {

        if(allMovies.size() == 6){
            for(int i = 0;i < 6;i++) {
                if (checkSingleData(allMovies.get(i))) {
                        return false;
                }
            }
        }else{
            return false;
        }

        return true;
    }

    private boolean checkSingleData(List<Movie> ml) {

        for(int j = 0;j < ml.size();j++){

            if(ml.get(j) == null){
                return false;
            }
        }
        return true;
    }

    private void setsavedInstanceLayout() {

        setViewPagerData();
        recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(allMovies, getContext(), top_titles, image_resources);
        recyclerView.setAdapter(recyclerViewCustomAdapter);
        recyclerViewCustomAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            popularMovies = savedInstanceState.getParcelableArrayList("popularMovies");
            highestRateMovies = savedInstanceState.getParcelableArrayList("highestRateMovies");
            highestGrossingMovies = savedInstanceState.getParcelableArrayList("highestGrossingMovies");
            upcomingMovies = savedInstanceState.getParcelableArrayList("upcomingMovies");
            thisYearReleaseMovies = savedInstanceState.getParcelableArrayList("thisYearReleaseMovies");
            movieList = savedInstanceState.getParcelableArrayList("movieList");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("popularMovies",popularMovies);
        outState.putParcelableArrayList("highestRateMovies",highestRateMovies);
        outState.putParcelableArrayList("highestGrossingMovies",highestGrossingMovies);
        outState.putParcelableArrayList("upcomingMovies",upcomingMovies);
        outState.putParcelableArrayList("thisYearReleaseMovies",thisYearReleaseMovies);
        outState.putParcelableArrayList("movieList",movieList);
    }

    private void setViewPagerData() {

        image_resources = new String[5];
        for(int i = 0;i < 5;i++){

            Movie m = movieList.get(i);
            image_resources[i] = m.getPoster_path();
        }
    }

    private void getPopularMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadPopularMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                popularMovies = (ArrayList<Movie>)movieResult.getResult();

                allMovies.add(popularMovies);
                String s ="hello world";
                getHighestRateMovies();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getHighestRateMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadHighestRatedMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                highestRateMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(highestRateMovies);
                String s ="hello world";
                getUpComingMovies();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getUpComingMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadUpcomingMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                upcomingMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(upcomingMovies);
                String s ="hello world";
                getHighestGrossingMovies();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getHighestGrossingMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadHighestGrossingMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                highestGrossingMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(highestGrossingMovies);
                String s ="hello world";
                getThisYearReleaseMovies();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void getThisYearReleaseMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadThisYearReleaseMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieResult = response.body();
                thisYearReleaseMovies = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(thisYearReleaseMovies);
                String s ="hello world";
                getMoviesList();

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
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
                movieList = (ArrayList<Movie>) movieResult.getResult();
                allMovies.add(movieList);

                loaded = true;

                int count = allMovies.size();
                String s = "hello";
                setViewPagerData();
                recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(allMovies, getContext(), top_titles, image_resources);
                recyclerView.setAdapter(recyclerViewCustomAdapter);
                recyclerViewCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }


}
package com.gosemathraj.popularmoviesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.adapters.InnerRecyclerViewCustomAdapter;
import com.gosemathraj.popularmoviesapp.database.DbHandler;
import com.gosemathraj.popularmoviesapp.models.Movie;
import com.gosemathraj.popularmoviesapp.models.MovieResult;
import com.gosemathraj.popularmoviesapp.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iamsparsh on 12/5/16.
 */
public class Fragment_All_Movies extends Fragment {

    private RecyclerView recyclerview;
    private MovieResult movieresult;
    private ArrayList<Movie> movies = new ArrayList<>();
    String s;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

            movies = savedInstanceState.getParcelableArrayList("movies");
            s = savedInstanceState.getString("state");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.all_movies_layout,container,false);

        recyclerview = (RecyclerView) view.findViewById(R.id.allmovies_recyclerview);

        s = this.getArguments().getString("variable");

        if (s.equals("popular")) {

            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getPopularMovies();
            }

        }
        if (s.equals("highestrated")) {

            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getHighestRatedMovies();
            }
        }
        if (s.equals("favourites")) {

            if(savedInstanceState != null){
                setRecyclerviewAdapter();
            }else{
                getFavouriteMovies();
            }
        }

        return view;
    }

    private void getFavouriteMovies() {

        DbHandler dbHandler = new DbHandler(getContext());
        movies = (ArrayList<Movie>) dbHandler.getAllMovies();

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies,getContext()));

    }

    private void getHighestRatedMovies() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadHighestRatedMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies, getContext()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });

    }

    public void getPopularMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieResult> call = movieApi.loadPopularMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {

                movieresult = response.body();
                movies = (ArrayList<Movie>) movieresult.getResult();

                recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                recyclerview.setHasFixedSize(true);
                recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies,getContext()));
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("movies", movies);
        outState.putString("state",s);
    }

    public void setRecyclerviewAdapter(){

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(new InnerRecyclerViewCustomAdapter(movies,getContext()));

    }
}

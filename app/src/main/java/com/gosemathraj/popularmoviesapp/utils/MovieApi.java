package com.gosemathraj.popularmoviesapp.utils;

import com.gosemathraj.popularmoviesapp.models.Movie;
import com.gosemathraj.popularmoviesapp.models.MovieResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by iamsparsh on 4/5/16.
 */
public interface MovieApi {

    @GET("/3/discover/movie?api_key=a6163838876304722868d717a499e5e1")
    Call<MovieResult> loadMovies();
}

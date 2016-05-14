package com.gosemathraj.popularmoviesapp.utils;

import com.gosemathraj.popularmoviesapp.models.Movie;
import com.gosemathraj.popularmoviesapp.models.MovieResult;
import com.gosemathraj.popularmoviesapp.models.MovieReviewsAll;
import com.gosemathraj.popularmoviesapp.models.MovieTrailers;
import com.gosemathraj.popularmoviesapp.models.MovieTrailersAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by iamsparsh on 4/5/16.
 */
public interface MovieApi {

    @GET("/3/discover/movie?api_key=a6163838876304722868d717a499e5e1")
    Call<MovieResult> loadMovies();

    @GET("/3/movie/{id}/videos?api_key=a6163838876304722868d717a499e5e1")
    Call<MovieTrailersAll> loadMovieTrailers(@Path("id") String id);

    @GET("/3/movie/{id}/reviews?api_key=a6163838876304722868d717a499e5e1")
    Call<MovieReviewsAll> loadMovieReviews(@Path("id") String id);

    @GET("/3/discover/movie?api_key=a6163838876304722868d717a499e5e1&sort_by=popularity.desc")
    Call<MovieResult> loadPopularMovies();

    @GET("/3/discover/movie?api_key=a6163838876304722868d717a499e5e1&sort_by=vote_average.desc")
    Call<MovieResult> loadHighestRatedMovies();

    @GET("/3/discover/movie?api_key=a6163838876304722868d717a499e5e1&sort_by=revenue.desc")
    Call<MovieResult> loadHighestGrossingMovies();

    @GET("/3/discover/movie?api_key=a6163838876304722868d717a499e5e1&primary_release_year=2016")
    Call<MovieResult> loadThisYearReleaseMovies();

    @GET("/3/movie/upcoming?api_key=a6163838876304722868d717a499e5e1")
    Call<MovieResult> loadUpcomingMovies();
}

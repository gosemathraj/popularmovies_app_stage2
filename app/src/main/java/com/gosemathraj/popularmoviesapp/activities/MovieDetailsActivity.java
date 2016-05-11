package com.gosemathraj.popularmoviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.fragments.Fragment_Movie_Details;
import com.gosemathraj.popularmoviesapp.fragments.Fragment_Movies_Home;
import com.gosemathraj.popularmoviesapp.models.Movie;

/**
 * Created by iamsparsh on 9/5/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private Movie m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        Intent intent = getIntent();
        m = (Movie)intent.getParcelableExtra("movie");

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.movie_details_linearlayout,new Fragment_Movie_Details()).commit();

    }

    public Movie returnMovie(){

        return m;
    }
}

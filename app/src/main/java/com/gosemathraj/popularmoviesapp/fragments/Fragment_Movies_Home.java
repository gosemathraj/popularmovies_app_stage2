package com.gosemathraj.popularmoviesapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.adapters.RecyclerViewCustomAdapter;
import com.gosemathraj.popularmoviesapp.adapters.ViewPaderAdapter;
import com.gosemathraj.popularmoviesapp.models.Movie;
import com.gosemathraj.popularmoviesapp.models.MovieResult;
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


    private CirclePageIndicator circlePageIndicator;
    private ViewPager viewPager;
    private MovieResult movieResult;
    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;
    private String[] image_resources;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_movies_home,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

        getMoviesList();
        return view;
    }

    private void setViewPagerData() {

        image_resources = new String[5];
        for(int i = 0;i < 5;i++){

            Movie m = movieList.get(i);
            image_resources[i] = m.getPoster_path();
        }

        viewPager.setAdapter(new ViewPaderAdapter(image_resources,getContext()));
        circlePageIndicator.setViewPager(viewPager);
        autoStartViewPager();
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
                recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(movieList, getContext());
                recyclerView.setAdapter(recyclerViewCustomAdapter);
                recyclerViewCustomAdapter.notifyDataSetChanged();
                setViewPagerData();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }

    private void autoStartViewPager() {

        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicator.setRadius(5 * density);

        NUM_PAGES =image_resources.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });
    }
}

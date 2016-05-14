package com.gosemathraj.popularmoviesapp.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.models.Movie;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by iamsparsh on 3/5/16.
 */
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<List<Movie>> allMovies;
    private List<Movie> movieList;
    private String[] image_resources;
    private Context context;
    private String[] toptitles;
    private InnerRecyclerViewCustomAdapter innerRecyclerViewCustomAdapter;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    public RecyclerViewCustomAdapter(List<List<Movie>> allMovies,Context context,String[] toptitles,String[] image_resources) {
        this.allMovies = allMovies;
        this.context = context;
        this.toptitles = toptitles;
        this.image_resources = image_resources;
        movieList = allMovies.get(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        if(viewType == 1){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout_one_sliderlayout,parent,false);
            vh = new MyViewHolder_LayoutOne(view);
        }else if(viewType == 2){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout_two_innerlayout,parent,false);
            vh = new MyViewHolder_LayoutTwo(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            movieList = allMovies.get(0);
        }
        if(position == 1){
            movieList = allMovies.get(0);
        }
        if(position == 2){
            movieList = allMovies.get(1);
        }
        if(position == 3){
            movieList = allMovies.get(2);
        }
        if(position == 4){
            movieList = allMovies.get(3);
        }
        if(position == 5){
            movieList = allMovies.get(5);
        }
        if(position == 6){
            movieList = allMovies.get(0);
        }

        switch(getItemViewType(position)){

            case 1:
                ((MyViewHolder_LayoutOne)holder).viewPager.setAdapter(new ViewPaderAdapter(image_resources, context,movieList));
                ((MyViewHolder_LayoutOne)holder).circlePageIndicator.setViewPager(((MyViewHolder_LayoutOne) holder).viewPager);

                autoStartViewPager((MyViewHolder_LayoutOne) holder, context);
                break;

            case 2:
                ((MyViewHolder_LayoutTwo)holder).textview_ttile.setText(toptitles[position - 1]);
                ((MyViewHolder_LayoutTwo)holder).inner_recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                ((MyViewHolder_LayoutTwo)holder).inner_recyclerView.setAdapter(new InnerRecyclerViewCustomAdapter(movieList, context));
                String s = "hello";
                break;
        }
    }

    private void autoStartViewPager(final MyViewHolder_LayoutOne holder,Context context) {

        final float density = context.getResources().getDisplayMetrics().density;
        holder.circlePageIndicator.setRadius(5 * density);

        NUM_PAGES =image_resources.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                holder.viewPager.setCurrentItem(currentPage++, true);
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
        holder.circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    @Override
    public int getItemCount() {
        return toptitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return 1;
        }else{
            return 2;
        }
    }

    public static class MyViewHolder_LayoutOne extends RecyclerView.ViewHolder{

        private View viewpager_view;
        private ViewPager viewPager;
        private CirclePageIndicator circlePageIndicator;

        public MyViewHolder_LayoutOne(View itemView) {
            super(itemView);

            viewpager_view = itemView;
            viewPager = (ViewPager) itemView.findViewById(R.id.view_pager);
            circlePageIndicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);

        }
    }

    public static class MyViewHolder_LayoutTwo extends RecyclerView.ViewHolder{

        TextView textview_ttile;
        RecyclerView inner_recyclerView;

        public MyViewHolder_LayoutTwo(View itemView) {
            super(itemView);

            textview_ttile = (TextView) itemView.findViewById(R.id.inner_recyclerview_top_title);
            inner_recyclerView = (RecyclerView) itemView.findViewById(R.id.inner_recyclerview);

        }
    }
}

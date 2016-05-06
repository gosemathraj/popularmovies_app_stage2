package com.gosemathraj.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iamsparsh on 3/5/16.
 */
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.MyViewHolder> {

    private List<Movie> movieList;
    private Context context;
    private String[] toptitles;
    private InnerRecyclerViewCustomAdapter innerRecyclerViewCustomAdapter;

    public RecyclerViewCustomAdapter(List<Movie> movieList,Context context,String[] toptitles) {
        this.movieList = movieList;
        this.context = context;
        this.toptitles = toptitles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_recycler_layout,parent,false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textview_ttile.setText(toptitles[position]);
        holder.inner_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.inner_recyclerView.setAdapter(new InnerRecyclerViewCustomAdapter(movieList,context));


    }

    @Override
    public int getItemCount() {
        return toptitles.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textview_ttile;
        RecyclerView inner_recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textview_ttile = (TextView) itemView.findViewById(R.id.inner_recyclerview_top_title);
            inner_recyclerView = (RecyclerView) itemView.findViewById(R.id.inner_recyclerview);

        }
    }
}

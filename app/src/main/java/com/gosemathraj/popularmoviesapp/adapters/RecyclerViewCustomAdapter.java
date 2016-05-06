package com.gosemathraj.popularmoviesapp.adapters;

import android.content.Context;
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

    public RecyclerViewCustomAdapter(List<Movie> movieList,Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_single_item_layout,parent,false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Movie m = movieList.get(position);
        holder.textview_ttile.setText(m.getTitle());

        Picasso.with(context).load("https://image.tmdb.org/t/p/w185"+m.getPoster_path()).resize(185,275).into(holder.imageview_banner);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageview_banner;
        TextView textview_ttile;
        public MyViewHolder(View itemView) {
            super(itemView);

            imageview_banner = (ImageView) itemView.findViewById(R.id.imageview_poster);
            textview_ttile = (TextView) itemView.findViewById(R.id.textview_title);
        }
    }
}

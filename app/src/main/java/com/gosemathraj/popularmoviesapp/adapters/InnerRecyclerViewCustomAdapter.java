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
 * Created by iamsparsh on 6/5/16.
 */
public class InnerRecyclerViewCustomAdapter extends RecyclerView.Adapter<InnerRecyclerViewCustomAdapter.InnerMyViewHolder>{

    private List<Movie> movieList;
    private Context context;

    public InnerRecyclerViewCustomAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public InnerMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inner_recyclerview_item_layout, parent, false);
        return new InnerMyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InnerMyViewHolder holder, int position) {

        Movie m = movieList.get(position);

        holder.poster_title.setText(m.getTitle());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185"+m.getPoster_path()).resize(185,278).into(holder.poster_imageView);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class InnerMyViewHolder extends RecyclerView.ViewHolder{

        private ImageView poster_imageView;
        private TextView poster_title;

        public InnerMyViewHolder(View itemView) {
            super(itemView);

            poster_imageView = (ImageView) itemView.findViewById(R.id.imageview_poster);
            poster_title = (TextView) itemView.findViewById(R.id.textview_title);
        }
    }
}

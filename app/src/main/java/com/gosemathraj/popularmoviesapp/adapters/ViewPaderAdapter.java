package com.gosemathraj.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gosemathraj.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by iamsparsh on 5/5/16.
 */
public class ViewPaderAdapter extends PagerAdapter{

    private String[] images;
    private Context context;
    private LayoutInflater inflater;

    public ViewPaderAdapter(String[] images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager_item_layout, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_slider_imageview);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + images[position]).into(imageView);
        container.addView(view);

        return view;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == (LinearLayout)object);
    }
}

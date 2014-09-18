package com.pifactorial.showcase.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pifactorial.showcase.domain.Thing;
import com.pifactorial.showcase.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import com.squareup.picasso.RequestCreator;
import com.etsy.android.grid.util.DynamicHeightImageView;

public class GalleryAdapter extends ArrayAdapter<Thing> {

    private final Activity mActivity;
    private final int mResource;
    private final List<Thing> mDataToShow;

    public GalleryAdapter( Activity activity, int resource, List<Thing> objects ) {
        super( activity, resource, objects );

        this.mActivity = activity;
        this.mResource = resource;
        this.mDataToShow = objects;

    }

    static class DealHolder {
        DynamicHeightImageView image;
        TextView title;
        TextView description;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        View row = convertView;
        final DealHolder holder;

        if ( row == null ) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            row = inflater.inflate( mResource, parent, false );

            holder = new DealHolder();
            holder.image = ( DynamicHeightImageView ) row.findViewById( R.id.image );
            holder.title = ( TextView ) row.findViewById( R.id.title );
            holder.description = ( TextView ) row.findViewById( R.id.description );

            row.setTag( holder );
        } else {
            holder = ( DealHolder ) row.getTag();
        }

        final Thing data = mDataToShow.get( position );

        RequestCreator request = Picasso.with( mActivity ).load( data.getProductUrl() );

        holder.title.setText( data.getName() );
        // TODO - Make this an icon with a URL
        // holder.description.setText( data.getCategory() );
        holder.image.setHeightRatio((data.getProductHeight() * 1.0) / data.getProductWidth());

        request.into(holder.image);

        return row;
    }

}

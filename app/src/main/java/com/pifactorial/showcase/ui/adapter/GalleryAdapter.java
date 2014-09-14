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
import com.pifactorial.showcase.ui.view.GalleryItemView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import com.squareup.picasso.RequestCreator;
import com.etsy.android.grid.util.DynamicHeightImageView;

public class GalleryAdapter extends ArrayAdapter<Thing> {

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    Activity mActivity;
    int mResource;
    List<Thing> mDataToShow;
    private Transformation cropPosterTransformation;

    public GalleryAdapter( Activity activity, int resource, List<Thing> objects ) {
        super( activity, resource, objects );

        this.mActivity = activity;
        this.mResource = resource;
        this.mDataToShow = objects;

        cropPosterTransformation = new Transformation() {

            @Override public Bitmap transform(Bitmap source) {
                int targetWidth = 200;
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, 300, 500, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override public String key() {
                return "cropPosterTransformation" + 200;
            }
        };
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


        // holder.image.setImageResource( Integer.parseInt( data.getImageUrl() ) );
        RequestCreator request = Picasso.with( mActivity ).load( data.getImageUrl() );
        // RequestCreator request = Picasso.with( mActivity ).load( data.getImageUrl() ).transform(cropPosterTransformation);

        request.into(holder.image);

        // double positionHeight = getPositionRatio( position );

        holder.title.setText( data.getName() );
        holder.description.setText( data.getCategory() );
        holder.image.setHeightRatio(1.0);


        return row;
    }


    private double getPositionRatio( final int position ) {
        double ratio = sPositionHeightRatios.get( position, 0.0 );
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if ( ratio == 0 ) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append( position, ratio );
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        // return ( new Random().nextDouble() / 2.0 ) + 1.0; // height will be 1.0 - 1.5 the width
        return ( 1 / 2.0 ) + 1.0; // height will be 1.0 - 1.5 the width
    }
}

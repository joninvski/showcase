package com.pifactorial.showcase.ui.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;
import com.pifactorial.showcase.R;
import com.pifactorial.showcase.domain.Ringtone;

import java.util.List;
import java.util.Random;

public class SampleAdapter extends ArrayAdapter<Ringtone> {

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    Activity mActivity;
    int mResource;
    List<Ringtone> mDataToShow;

    public SampleAdapter( Activity activity, int resource, List<Ringtone> objects ) {
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

        final Ringtone data = mDataToShow.get( position );

        // holder.image.setImageResource( Integer.parseInt( data.getUri() ) );
        Picasso.with( mActivity ).load( data.getUri() ).into( holder.image );

        double positionHeight = getPositionRatio( position );

        holder.image.setHeightRatio( positionHeight );

        holder.title.setText( data.getName() );
        holder.description.setText( data.getPriceString() );

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
        return ( new Random().nextDouble() / 2.0 ) + 1.0; // height will be 1.0 - 1.5 the width
    }
}

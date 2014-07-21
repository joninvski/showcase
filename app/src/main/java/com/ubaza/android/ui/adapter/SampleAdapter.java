package com.ubaza.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.ubaza.android.R;
import com.ubaza.domain.Ringtone;

import hugo.weaving.DebugLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;
import com.squareup.picasso.Picasso;


public class SampleAdapter extends ArrayAdapter<Ringtone> {

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    Activity activity;
    int resource;
    List<Ringtone> datas;

    public SampleAdapter( Activity activity, int resource, List<Ringtone> objects ) {
        super( activity, resource, objects );

        this.activity = activity;
        this.resource = resource;
        this.datas = objects;
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
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate( resource, parent, false );

            holder = new DealHolder();
            holder.image = ( DynamicHeightImageView ) row.findViewById( R.id.image );
            holder.title = ( TextView ) row.findViewById( R.id.title );
            holder.description = ( TextView ) row.findViewById( R.id.description );

            row.setTag( holder );
        } else {
            holder = ( DealHolder ) row.getTag();
        }

        final Ringtone data = datas.get( position );

        // holder.image.setImageResource( Integer.parseInt( data.getUri() ) );
        Picasso.with(activity).load(data.getUri()).into(holder.image);

        double positionHeight = getPositionRatio(position);

        holder.image.setHeightRatio( positionHeight );

        holder.title.setText( data.getName() );
        holder.description.setText( data.getPriceString() );

        return row;
    }

  private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (new Random().nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}

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

public class SampleAdapter extends ArrayAdapter<Ringtone> {
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

        holder.image.setImageResource( Integer.parseInt( data.getUri() ) );
        holder.image.setHeightRatio( 0.5 );

        holder.title.setText( data.getName() );
        holder.description.setText( data.getPriceString() );

        return row;
    }
}

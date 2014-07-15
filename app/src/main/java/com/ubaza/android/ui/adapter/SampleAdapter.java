package com.ubaza.android.ui.adapter;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.etsy.android.grid.util.DynamicHeightTextView;
import hugo.weaving.DebugLog;
import timber.log.Timber;
import com.ubaza.domain.Ringtone;
import android.app.Activity;
import java.util.List;
import com.etsy.android.grid.util.DynamicHeightImageView;
import android.widget.TextView;
import com.ubaza.android.R;


public class SampleAdapter extends ArrayAdapter<Ringtone> {
    Activity activity;
    int resource;
    List<Ringtone> datas;

    public SampleAdapter(Activity activity, int resource, List<Ringtone> objects) {
        super(activity, resource, objects);

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final DealHolder holder;

        if (row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new DealHolder();
            holder.image = (DynamicHeightImageView) row.findViewById(R.id.image);
            holder.title = (TextView)row.findViewById(R.id.title);
            holder.description = (TextView)row.findViewById(R.id.description);

            row.setTag(holder);
        }
        else {
            holder = (DealHolder) row.getTag();
        }

        final Ringtone data = datas.get(position);
        holder.image.setImageResource(R.drawable.doge);

        Random r = new Random();
        holder.image.setHeightRatio(r.nextFloat());
        holder.title.setText(data.getName());
        holder.description.setText(data.getUri());

        return row;
    }
}

package com.pifactorial.showcase.ui.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pifactorial.showcase.domain.Call;
import com.pifactorial.showcase.domain.Thing;
import com.pifactorial.showcase.R;
import com.pifactorial.showcase.ui.adapter.GalleryAdapter;
import com.pifactorial.showcase.ui.common.BaseFragment;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import hugo.weaving.DebugLog;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ThingDetailFragment extends BaseFragment {

    // Layout views
    @InjectView(R.id.thing_text) TextView mText;  // The esty style grid cards view
    @InjectView(R.id.detail_image) ImageView mImage;  // The esty style grid cards view

    public static ThingDetailFragment newInstance(String url, String text) {
        ThingDetailFragment f = new ThingDetailFragment();

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("text", text);
        f.setArguments(args);

        return f;
    }

    @DebugLog
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_thing_detail, container, false );
        ButterKnife.inject(this, view);

        String text = getArguments().getString("text");
        mText.setText(text);

        String url = getArguments().getString("url");
        RequestCreator request = Picasso.with( getActivity() ).load( url );
        request.into( mImage );

        return view;
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    @Override
    @DebugLog
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
    }
}

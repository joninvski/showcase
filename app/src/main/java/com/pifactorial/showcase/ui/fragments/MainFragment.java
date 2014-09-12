package com.pifactorial.showcase.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.pifactorial.showcase.domain.Call;
import com.pifactorial.showcase.domain.Thing;
import com.pifactorial.showcase.R;
import com.pifactorial.showcase.ui.adapter.SampleAdapter;
import com.pifactorial.showcase.ui.common.BaseFragment;
import com.squareup.otto.Subscribe;

import hugo.weaving.DebugLog;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    // Layout views
    private StaggeredGridView gridView;      // The esty style grid cards view
    private SwipeRefreshLayout mSwipeLayout; // The pull down to refresh view

    // Private members
    private SampleAdapter mAdapter;          // The adapter to show the things
    private List<Thing> mThings = new ArrayList<Thing>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public void getThingsAssynchronasly() {
        getRestClient().getThings();
    }

    @Subscribe
    public void onAvailableThingsUpdate( ArrayList<Thing> thingList ) {
        Timber.d( "Setting the things in the view (called by otto)" );
        StringBuilder sBuild = new StringBuilder();
        for( Thing thing : thingList ) {
            sBuild.append( thing.toString() + '\n' );
        }
        Timber.d( "Setting these things %s", sBuild.toString() );

        mThings.clear();
        mThings.addAll( thingList );

        mAdapter.notifyDataSetChanged();
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getThingsAssynchronasly();

        getCalls();
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
    }

    @DebugLog
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_main, container, false );

        gridView = ( StaggeredGridView ) view.findViewById( R.id.grid_view );

        mAdapter = new SampleAdapter( getActivity(), R.layout.list_item_sample, mThings );
        gridView.setAdapter( mAdapter );

        mSwipeLayout = ( SwipeRefreshLayout ) view.findViewById( R.id.swipe_view );
        mSwipeLayout.setColorSchemeResources( android.R.color.holo_blue_bright,
                                              android.R.color.holo_green_light,
                                              android.R.color.holo_orange_light,
                                              android.R.color.holo_red_light );
        mSwipeLayout.setOnRefreshListener( this );

        return view;
    }

    @Override
    public void onRefresh() {
        Timber.d( "User asked to to refresh the things" );
        getThingsAssynchronasly();
    }

    @DebugLog
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    @DebugLog
    public void getCalls() {
        if( getCounterService() != null ) {
            List<Call> calls = getCounterService().getCalls();
        }
    }

    @Override
    @DebugLog
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
    }
}

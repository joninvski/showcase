package com.ubaza.android.ui.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import butterknife.OnClick;

import com.etsy.android.grid.StaggeredGridView;
import com.squareup.otto.Subscribe;
import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.adapter.SampleAdapter;
import com.ubaza.android.ui.common.BaseFragment;
import com.ubaza.domain.Call;
import com.ubaza.domain.Ringtone;
import com.ubaza.rest.UbazaRestClient;

import hugo.weaving.DebugLog;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import timber.log.Timber;
import android.support.v4.widget.SwipeRefreshLayout;

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private UbazaRestClient ubazaRest;
    private List<Ringtone> ringtones = new ArrayList<Ringtone>();
    SampleAdapter mAdapter;
    SwipeRefreshLayout swipeLayout;
    StaggeredGridView gridView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public void getRingTonesAssynchronasly() {
        ubazaRest.getRingtones();
    }

    @Subscribe
    public void onAvailableRingtonesUpdate( ArrayList<Ringtone> ringToneList ) {
        Timber.d( "Setting the ringtones in the view (called by otto)" );
        StringBuilder sBuild = new StringBuilder();
        for( Ringtone ring : ringToneList ) {
            sBuild.append( ring.toString() + '\n' );
        }
        Timber.d( "Setting these ringtones %s", sBuild.toString() );

        ringtones.clear();
        ringtones.addAll( ringToneList );

        mAdapter.notifyDataSetChanged();
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getBus().register( this );
        getRingTonesAssynchronasly();

        getCalls();
    }

    @DebugLog
    @Override
    public void onPause() {
        getBus().unregister( this );
        super.onPause();
    }

    @DebugLog
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_main, container, false );
        ubazaRest = new UbazaRestClient( getBus() );

        gridView = ( StaggeredGridView ) view.findViewById( R.id.grid_view );

        mAdapter = new SampleAdapter( getActivity(), R.layout.list_item_sample, ringtones );
        gridView.setAdapter( mAdapter );

        swipeLayout = ( SwipeRefreshLayout ) view.findViewById( R.id.swipe_view );
        swipeLayout.setColorSchemeResources( android.R.color.holo_blue_bright,
                                             android.R.color.holo_green_light,
                                             android.R.color.holo_orange_light,
                                             android.R.color.holo_red_light );
        swipeLayout.setOnRefreshListener( this );

        return view;
    }

    @Override
    public void onRefresh() {
        getRingTonesAssynchronasly();
        new Handler().postDelayed( new Runnable() {
            @Override public void run() {
                Timber.d( "Inside on Refresh" );
                swipeLayout.setRefreshing( false );
            }
        }, 5000 );
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
            Timber.d( "%s", Call.toString( calls ) );
        }
    }

    @Override
    @DebugLog
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
    }
}

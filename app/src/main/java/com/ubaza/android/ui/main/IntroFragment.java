package com.ubaza.android.ui.main;


import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.common.BaseFragment;
import com.ubaza.domain.Call;
import com.ubaza.domain.Ringtone;
import com.ubaza.rest.UbazaRestClient;

import hugo.weaving.DebugLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;

import retrofit.client.Response;

import retrofit.RestAdapter;

import retrofit.RetrofitError;
import java.lang.StringBuilder;
import timber.log.Timber;

public class IntroFragment extends BaseFragment {

    private CounterService mCounterService;
    private ServiceConnection mSvcConn = startService();
    private UbazaRestClient ubazaRest;

    /* Fragment views */
    private TextView tvCallsReceived;
    private TextView tvRingtones;
    private Button btStartService;
    private Button btGetRingtones;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    public IntroFragment() {
        // Required empty public constructor
    }

    public ServiceConnection startService() {
        return new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                Timber.d("New service connection to:  %s \n", className);
                CounterService.LocalBinder binder = (CounterService.LocalBinder) service;
                mCounterService = binder.getService();
            }

            public void onServiceDisconnected(ComponentName className) {
                Timber.d("Service has crashed", className);
            }
        };
    }

    public void restTest() {
        ubazaRest.getRingtones();
    }

    @Subscribe
    public void setRingtones(ArrayList<Ringtone> ringToneList) {
        Toast.makeText(getActivity(), "Received Otto", Toast.LENGTH_SHORT).show();
        StringBuilder sBuild = new StringBuilder();
        for( Ringtone ring : ringToneList )
            sBuild.append(ring.toString() + '\n');

        tvRingtones.setText(sBuild.toString());
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getBus().register(this);
        getCalls();
    }

    @DebugLog
    @Override
    public void onPause() {
        getBus().unregister(this);
        super.onPause();
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_intro, container, false);
        tvCallsReceived = (TextView) view.findViewById(R.id.received_calls);
        tvRingtones = (TextView) view.findViewById(R.id.ringtones);
        ubazaRest = new UbazaRestClient(getBus());

        return view;
    }

    @DebugLog
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize button with listener
        btStartService = (Button) view.findViewById(R.id.start_service);
        btStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Clicked start service view");
                startCallListenerService();
            }
        });

        btGetRingtones = (Button) view.findViewById(R.id.get_ringtones);
        btGetRingtones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Clicked getRingtones");
                restTest();
            }
        });
    }

    @DebugLog
    public void startCallListenerService() {
        Timber.d("Calling bindService()");
        getActivity().bindService(CounterService.makeIntent(getActivity()),
                mSvcConn,
                Context.BIND_AUTO_CREATE);
    }

    @DebugLog
    public void getCalls() {
        if(mCounterService != null) {
            List<Call> calls = mCounterService.getCalls();
            tvCallsReceived.setText(Call.toString(calls));
        }
    }

    @Override
    @DebugLog
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}

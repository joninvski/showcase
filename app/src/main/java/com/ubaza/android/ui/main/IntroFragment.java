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

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.common.BaseFragment;

import hugo.weaving.DebugLog;

import javax.inject.Inject;
import com.ubaza.domain.Call;
import java.util.List;
import retrofit.RestAdapter;
import com.ubaza.rest.UbazaRestClient;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.RetrofitError;
import com.ubaza.domain.Ringtone;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class IntroFragment extends BaseFragment {

    private static final String TAG = "IntroFragment";

    private TextView tvCallsReceived;
    private Button btStartService;
    private Button btGetRingtones;
    private CounterService mCounterService;
    private ServiceConnection mSvcConn = startService();

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    public IntroFragment() {
        // Required empty public constructor
    }

    public ServiceConnection startService() {
        return new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                Log.d(TAG, "New service connection");
                CounterService.LocalBinder binder = (CounterService.LocalBinder) service;
                mCounterService = binder.getService();
            }

            public void onServiceDisconnected(ComponentName className) {
                Log.d(TAG, "Service has crashed");
            }
        };
    }

    public void restTest() {
        UbazaRestClient ubaza = new UbazaRestClient(getBus());
        ubaza.getRingtones();
    }

    @Subscribe public void setRingtones(ArrayList<Ringtone> event) {
        Toast.makeText(getActivity(), "Received Otto", Toast.LENGTH_SHORT).show();
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
        super.onPause();
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_intro, container, false);
        tvCallsReceived = (TextView) view.findViewById(R.id.received_ringtones);
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
                startCallListenerService();
                Log.d(TAG, "Clicked start service view");
            }
        });

        btGetRingtones = (Button) view.findViewById(R.id.get_ringtones);
        btGetRingtones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTest();
                Log.d(TAG, "Clicked getRingtones");
            }
        });
    }

    @DebugLog
    public void startCallListenerService() {
        Log.d(TAG, "calling bindService()");
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

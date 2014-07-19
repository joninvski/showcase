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

import butterknife.ButterKnife;
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

public class MainFragment extends BaseFragment {

    private UbazaRestClient ubazaRest;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment () {
        // Required empty public constructor
    }

    public void restTest() {
        ubazaRest.getRingtones();
    }

    @Subscribe
    public void setRingtones(ArrayList<Ringtone> ringToneList) {
        Timber.d("Setting the ringtones in the view (called by otto)");
        StringBuilder sBuild = new StringBuilder();
        for( Ringtone ring : ringToneList )
            sBuild.append(ring.toString() + '\n');
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
        ubazaRest = new UbazaRestClient(getBus());

        StaggeredGridView gridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        SampleAdapter mAdapter = new SampleAdapter(getActivity(), R.layout.list_item_sample, generateSampleData());
        gridView.setAdapter(mAdapter);

        return view;
    }

    public static ArrayList<Ringtone> generateSampleData() {
        String repeat = " repeat";
        final int SAMPLE_DATA_ITEM_COUNT = 10;
        final ArrayList<Ringtone> datas = new ArrayList<Ringtone>();
        for (int i = 0; i < SAMPLE_DATA_ITEM_COUNT; i++) {
            Ringtone data = new Ringtone("CardA", "https://jiresal-test.s3.amazonaws.com/deal3.png");
            Random ran = new Random();
            int x = ran.nextInt(i + SAMPLE_DATA_ITEM_COUNT);
            for (int j = 0; j < x; j++)
            datas.add(data);
        }
        return datas;
    }

    @DebugLog
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @DebugLog
    public void getCalls() {
        if(getCounterService() != null) {
            List<Call> calls = getCounterService().getCalls();
            Timber.d("%s", Call.toString(calls));
        }
    }

    @Override
    @DebugLog
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}

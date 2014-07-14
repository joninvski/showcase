package com.spengilley.activityfragmentmvp.ui.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;

import android.util.Log;

import android.widget.Toast;

import com.spengilley.activityfragmentmvp.R;
import com.spengilley.activityfragmentmvp.services.CounterService;
import com.spengilley.activityfragmentmvp.ui.common.BaseActivity;
import com.spengilley.activityfragmentmvp.ui.main.presenters.MainPresenterImpl;
import com.spengilley.activityfragmentmvp.ui.main.views.MainView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainView, FragmentCallback {
    private final static String TAG = "MAINACTIVITY";

    @Inject
    MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load IntroFragment
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, IntroFragment.newInstance()).commit();
    }


    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    /**
     * FragmentCallback implementation
     */
    @Override
    public void loadDetailFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, DetailsFragment.newInstance()).commit();
    }

    @Override
    public void finishProcess() {
        finish();
    }

    /**
     * Hook method called by Android when this Activity becomes
     * visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");

    }
}

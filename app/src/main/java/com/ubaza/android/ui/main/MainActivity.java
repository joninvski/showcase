package com.ubaza.android.ui.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;

import android.widget.Toast;

import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.common.BaseActivity;

import hugo.weaving.DebugLog;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load IntroFragment
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, IntroFragment.newInstance()).commit();
    }

    @DebugLog
    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    /**
     * Hook method called by Android when this Activity becomes
     * visible.
     */
    @Override
    @DebugLog
    protected void onStart() {
        super.onStart();
    }
}

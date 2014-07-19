package com.ubaza.android.ui.activities;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.Toast;

import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.common.BaseActivity;
import com.ubaza.android.ui.fragments.MainFragment;

import hugo.weaving.DebugLog;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance()).commit();
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

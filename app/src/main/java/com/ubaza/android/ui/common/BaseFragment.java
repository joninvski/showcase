package com.ubaza.android.ui.common;

import android.app.Fragment;
import android.os.Bundle;
import com.ubaza.android.application.App;
import com.squareup.otto.Bus;
import com.ubaza.android.services.CounterService;
import butterknife.ButterKnife;
import android.view.View;
import hugo.weaving.DebugLog;

/*
 * Provides an abstraction for all fragments to extend
 *
 * It takes care of otto bus register/unregister events.
 * Fragments do not have to do this. Only post or receive events
 *
 * It also provides some usefull getApplication and getCounterService methods
 * */
public class BaseFragment extends Fragment {

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getBus().register( this );
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
        getBus().unregister( this );
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        ButterKnife.inject( this, view );
    }

    public App getApplication() {
        return (App) getActivity().getApplication();
    }

    public CounterService getCounterService() {
        return ((BaseActivity) getActivity()).getCounterService();
    }

    public Bus getBus() {
        return getApplication().getBus();
    }
}

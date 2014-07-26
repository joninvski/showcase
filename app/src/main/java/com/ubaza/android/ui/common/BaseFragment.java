package com.ubaza.android.ui.common;

import android.app.Fragment;
import android.os.Bundle;
import com.ubaza.android.application.App;
import com.squareup.otto.Bus;
import com.ubaza.android.services.CounterService;
import butterknife.ButterKnife;
import android.view.View;


public class BaseFragment extends Fragment {

    public App getApplication() {
        return (App) getActivity().getApplication();
    }

    public CounterService getCounterService() {
        return ((BaseActivity) getActivity()).getCounterService();
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        getBus().register(this);
        ButterKnife.inject( this, view );
    }

    public Bus getBus() {
        return getApplication().getBus();
    }
}

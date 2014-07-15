package com.ubaza.android.ui.common;

import android.app.Fragment;
import android.os.Bundle;
import com.ubaza.android.App;
import com.squareup.otto.Bus;


public class BaseFragment extends Fragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BaseActivity) getActivity()).inject(this);
    }

    public App getApplication() {
        return (App) getActivity().getApplication();
    }

    public Bus getBus() {
        return getApplication().mBus;
    }
}

package com.ubaza.android.ui.fragments;

import com.ubaza.android.ui.common.BaseFragment;
import hugo.weaving.DebugLog;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import com.ubaza.android.R;

public class AlternativeFragment extends BaseFragment {

    public static AlternativeFragment newInstance() {
        return new AlternativeFragment();
    }

    @DebugLog
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_alternative, container, false );

        return view;
    }
}

package com.ubaza.android.ui.main.presenters;


import com.ubaza.android.ui.main.views.IntroView;

import javax.inject.Inject;

public class IntroPresenterImpl implements IntroPresenter {
    private IntroView view;

    @Inject
    public IntroPresenterImpl() {

    }

    @Override
    public void init(IntroView view) {
        this.view = view;
    }


    @Override
    public void getDetails() {
        // Do stuff to get details
        // then report back to view
    }
}

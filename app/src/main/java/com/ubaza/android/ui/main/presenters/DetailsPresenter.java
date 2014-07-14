package com.ubaza.android.ui.main.presenters;

import com.ubaza.android.ui.common.BaseFragmentPresenter;
import com.ubaza.android.ui.main.views.DetailsView;

public interface DetailsPresenter extends BaseFragmentPresenter<DetailsView> {

    public void getDetails();
    public void doStuffThenFinish();

}

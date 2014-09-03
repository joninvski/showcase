package com.pifactorial.showcase.services;

import android.app.Service;

import com.squareup.otto.Bus;
import com.pifactorial.showcase.application.App;
import com.pifactorial.showcase.rest.ShowcaseRestClient;

public abstract class BaseService extends Service {

    protected App getShowcaseApplication() {
        return ( App ) getApplication();
    }

    public Bus getBus() {
        return getShowcaseApplication().getBus();
    }

    public ShowcaseRestClient getRestClient() {
        return getShowcaseApplication().getShowcaseRestClient();
    }
}

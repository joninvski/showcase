package com.ubaza.android.services;

import android.app.Service;

import com.squareup.otto.Bus;
import com.ubaza.android.application.App;
import com.ubaza.rest.UbazaRestClient;

public abstract class BaseService extends Service {

    protected App getUbazaApplication() {
        return ( App ) getApplication();
    }

    public Bus getBus() {
        return getUbazaApplication().getBus();
    }

    public UbazaRestClient getRestClient() {
        return getUbazaApplication().getUbazaRestClient();
    }
}

package com.ubaza.android.services;

import com.squareup.otto.Bus;
import com.ubaza.android.application.App;
import android.app.Service;

public abstract class BaseService extends Service {

    protected App getUbazaApplication() {
        return ( App ) getApplication();
    }

    public Bus getBus() {
        return getUbazaApplication().getBus();
    }
}

package com.ubaza.android.services;

import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;

public class BootServiceLauncher extends BroadcastReceiver {
    @Override
    public void onReceive( Context context, Intent intent ) {
        Intent startServiceIntent = new Intent( context, CounterService.class );
        context.startService( startServiceIntent );
    }
}

package com.ubaza.android.ui.activities;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.InjectView;

import com.ubaza.android.R;
import com.ubaza.android.services.CounterService;
import com.ubaza.android.ui.common.BaseActivity;
import com.ubaza.android.ui.fragments.MainFragment;

import hugo.weaving.DebugLog;

import java.util.Arrays;
import java.util.List;
import android.widget.ArrayAdapter;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import butterknife.OnItemClick;
import timber.log.Timber;
import com.ubaza.android.ui.fragments.AlternativeFragment;

public class MainActivity extends BaseActivity {

    @InjectView( R.id.left_drawer ) ListView mDrawerList;
    @InjectView( R.id.drawer_layout ) DrawerLayout mDrawer;

    @DebugLog
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ButterKnife.inject( this );
        String[] data = {"one", "two"};

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled( true );
        getActionBar().setHomeButtonEnabled( true );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, data );

        mDrawerList.setAdapter( adapter );

        getFragmentManager().beginTransaction()
        .replace( R.id.fragment_container, MainFragment.newInstance() ).commit();
    }

    @DebugLog
    @OnItemClick( R.id.left_drawer )
    protected void onLeftMenuItemClick( final int pos ) {
        mDrawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed( View drawerView ) {
                super.onDrawerClosed( drawerView );
                Timber.d( "Selected pos %d", pos );
                if( pos == 0 ) {
                    getFragmentManager().beginTransaction().replace( R.id.fragment_container, MainFragment.newInstance() ).commit();
                } else {
                    getFragmentManager().beginTransaction().replace( R.id.fragment_container, AlternativeFragment.newInstance() ).commit();
                }
            }
        } );
        mDrawer.closeDrawer( mDrawerList );
    }

    /**
     * Hook method called by Android when this Activity becomes
     * visible.
     */
    @Override
    @DebugLog
    protected void onStart() {
        super.onStart();
    }
}

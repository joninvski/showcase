package com.ubaza.android.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.ubaza.android.R;
import com.ubaza.android.ui.common.BaseActivity;
import com.ubaza.android.ui.fragments.AlternativeFragment;
import com.ubaza.android.ui.fragments.MainFragment;

import hugo.weaving.DebugLog;

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @InjectView( R.id.left_drawer ) ListView mDrawerList;
    @InjectView( R.id.drawer_layout ) DrawerLayout mDrawer;

    @DebugLog
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ButterKnife.inject( this );
        String[] data = {"Main Layout", "Test Layout", "Send fake call"};

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setHomeButtonEnabled( true );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getSupportActionBar().getThemedContext(), android.R.layout.simple_list_item_1, data );

        mDrawerList.setAdapter( adapter );

        getFragmentManager().beginTransaction().replace( R.id.fragment_container, MainFragment.newInstance()).commit();
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
                }
                else if( pos == 1 ) {
                    getFragmentManager().beginTransaction().replace( R.id.fragment_container, AlternativeFragment.newInstance() ).commit();
                }
                else if( pos == 2 ) {
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

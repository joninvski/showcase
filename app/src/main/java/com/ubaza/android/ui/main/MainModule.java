package com.ubaza.android.ui.main;

import com.ubaza.android.AppModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = AppModule.class,
        injects = {
                MainActivity.class,
                IntroFragment.class,
        },
        complete = false,
        library = true
)

public class MainModule {
    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }
}

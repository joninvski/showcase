package com.ubaza.android.ui.main;

import com.ubaza.android.AppModule;
import com.ubaza.android.ui.main.presenters.IntroPresenter;
import com.ubaza.android.ui.main.presenters.IntroPresenterImpl;
import com.ubaza.android.ui.main.presenters.MainPresenter;
import com.ubaza.android.ui.main.presenters.MainPresenterImpl;
import com.ubaza.android.ui.main.views.MainView;

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


    /**
     * Provide MainView
     */
    @Provides
    @Singleton
    public MainView provideMainView() {
        return (MainView) activity;
    }


    /**
     * Provide MainPresenter
     */
    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(MainView view) {
        return new MainPresenterImpl(view);
    }

    /**
     * Provide IntroPresenter
     */
    @Provides
    @Singleton
    public IntroPresenter provideIntroPresenter() {
        return new IntroPresenterImpl();
    }
}

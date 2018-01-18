package com.app.ecosurvey.module;

import android.content.Context;

import com.app.ecosurvey.ui.Presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    MainPresenter provideHomePresenter(Context context) {
        return new MainPresenter(context);
    }

}


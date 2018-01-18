package com.app.ecosurvey.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.ecosurvey.ui.Realm.RealmController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;
    private Context context;

    public AppModule(Application application, Context context) {
        mApplication = application;
        this.context = context;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPref(Context context) {
        return context.getSharedPreferences("RiderPreferences", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    RealmController provideMainPresenter(Context context) {
        return new RealmController(context);
    }


}



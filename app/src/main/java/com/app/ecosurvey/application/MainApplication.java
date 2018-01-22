package com.app.ecosurvey.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.app.ecosurvey.module.AppModule;
import com.app.ecosurvey.module.NetModule;
import com.app.ecosurvey.module.PresenterModule;
import com.app.ecosurvey.module.component.AppComponent;
import com.app.ecosurvey.module.component.DaggerAppComponent;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;


public class MainApplication extends Application {

    private static Activity instance;
    private AppComponent mDataComponent;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

        context = getApplicationContext();
        buildComponentAndInject();

    }

    public AppComponent getNetComponent() {
        return mDataComponent;
    }

    public void buildComponentAndInject() {
        mDataComponent = DaggerComponentInitializer.init(this, context);
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public static Activity getContext() {
        return instance;
    }

    public static final class DaggerComponentInitializer {
        public static AppComponent init(MainApplication app, Context context) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(app, context))
                    .netModule(new NetModule())
                    .presenterModule(new PresenterModule())
                    .build();
        }
    }

    public static AppComponent component(Context context) {
        return ((MainApplication) context.getApplicationContext()).mDataComponent;
    }

}


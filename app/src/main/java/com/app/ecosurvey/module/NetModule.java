package com.app.ecosurvey.module;

import android.content.Context;

import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.api.ApiRequestHandler;
import com.app.ecosurvey.api.ApiService;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    Interceptor provideRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();

                long t1 = System.nanoTime();
                //logger.info(String.format("Sending request %s on %s%n%s",request.url(), chain.connection(), request.headers()));
                Response response = chain.proceed(request);

                long t2 = System.nanoTime();
                //Log.e(String.format("Received response for %s in %.1fms%n%s",response.request().url(), (t2 - t1) / 1e6d, response.headers()));

                return response;
            }
        };
    }

    @Provides
    @Singleton
    ApiService provideApiService(Interceptor requestInterceptor) {

        final ApiService service;

        int MAX_IDLE_CONNECTIONS = 30 * 60 * 1000;
        int KEEP_ALIVE_DURATION_MS = 3 * 60 * 1000;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .addHeader("Accept", "application/json2")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoint.getUrl())
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return service = retrofit.create(ApiService.class);

    }

    @Provides
    @Singleton
    ApiRequestHandler apiRequestHandler(Context context) {
        return new ApiRequestHandler(context);
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus(ThreadEnforcer.ANY);
    }

}


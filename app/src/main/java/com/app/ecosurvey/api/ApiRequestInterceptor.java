package com.app.ecosurvey.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRequestInterceptor implements Interceptor {

    private final String apiKey;

    public ApiRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    /*@Override
    public void intercept() {
        request.addHeader("Content-Type", "application/json");

        //request.addHeader("X-Mashape-Key", apiKey);
    }*/

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        //logger.info(String.format("Sending request %s on %s%n%s",request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        //Log.e(String.format("Received response for %s in %.1fms%n%s",response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}

package com.preethzcodez.ecommerce.webservice;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private Context context;

    public RetrofitBuilder(Context context) {
        this.context = context;
    }

    public OkHttpClient setClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .build();
    }

    public OkHttpClient setClient1C(String name, String password) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                //.authenticator(getBasicAuthenticator(name, password))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "application/json;versions=1");

                        ongoing.addHeader("Authorization", Credentials.basic(name, password));

                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        return okHttpClient;
    }

    public Retrofit retrofitBuilder(OkHttpClient httpClient, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }


    private static Authenticator getBasicAuthenticator(final String userName, final String password) {
        return (route, response) -> {
            String credential = Credentials.basic(userName, password);
            return response.request().newBuilder().header("Authorization", credential).build();
        };
    }
}

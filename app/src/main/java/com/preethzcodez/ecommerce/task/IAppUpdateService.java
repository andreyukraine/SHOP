package com.preethzcodez.ecommerce.task;

import com.preethzcodez.ecommerce.task.AppFileData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAppUpdateService {
    @GET("version.txt")
    Call<AppFileData> getLatestAppInfo(@Query("extraKey") String extraKey, @Query("extraValue") String extraValue);
}

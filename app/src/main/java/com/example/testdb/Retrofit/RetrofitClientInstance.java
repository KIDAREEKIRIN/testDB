package com.example.testdb.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    // BaseUrl등록
    private static final String BASE_URL = "http://3.37.249.79/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // scalar
                    .addConverterFactory(ScalarsConverterFactory.create())
                    // Json을 변환해줄 Gson변환기 등록
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

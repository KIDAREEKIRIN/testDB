package com.example.testdb.Retrofit;

import com.example.testdb.DutyExample.DTO.DutyName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("test5/DutyExample/DutyExample_read.php")
    Call<List<DutyName>> getAllDutyNames();

}

package com.example.testdb.Retrofit;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.DutyExample.DTO.DutyTitle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {

    @GET("test5/DutyExample/DutyExample_read.php")
    Call<List<DutyName>> getAllDutyNames();

    @GET("test5/DutyExample/title_read.php")
    Call<List<DutyTitle>> getAllTitles();

    @POST("test5/DutyExample/title_insert.php")
    Call<DutyTitle> insertDutyTitle();
}

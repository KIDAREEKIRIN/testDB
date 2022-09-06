package com.example.testdb.Retrofit;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.DutyExample.DTO.DutyTitle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {

    @GET("test5/DutyExample/DutyExample_read.php")
    Call<List<DutyName>> getAllDutyNames();

    @GET("test5/DutyExample/title_read.php")
    Call<List<DutyTitle>> getAllTitles();

    // 업무 제목 추가하기
    @FormUrlEncoded
    @POST("test5/DutyExample/title_insert.php")
    Call<DutyTitle> insertDutyTitle(
            @Field("title_name") String title_name,
            @Field("duty_id") Integer duty_id
    );

    // 업무 제목 수정하기
    @FormUrlEncoded
    @POST("test5/DutyExample/title_edit.php")
    Call<DutyTitle> editDutyTitle(
            @Field("title_id") Integer title_id,
            @Field("title_name") String title_name
    );

    // 업무 제목 삭제하기
    @FormUrlEncoded
    @POST("test5/DutyExample/title_delete.php")
    Call<DutyTitle> deleteTitle(
            @Field("title_id") Integer title_id
    );
}

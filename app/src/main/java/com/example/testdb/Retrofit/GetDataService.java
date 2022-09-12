package com.example.testdb.Retrofit;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.DutyExample.DTO.DutyStep;
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

    // 업무 단계 조회하기.
    @GET("test5/DutyExample/step_read.php")
    Call<List<DutyStep>> getAllDutySteps();

    // 업무 단계 추가하기.
    @FormUrlEncoded
    @POST("test5/DutyExample/step_insert.php")
    Call<DutyStep> insertStep(
            @Field("step_name") String step_name,
            @Field("title_id") Integer title_id
    );

    // Title_step 리스트 표현하기.
    @FormUrlEncoded
    @POST("test5/DutyExample/steps_read.php")
    Call<List<DutyStep>> getSteps(
            @Field("title_id") Integer title_id
    );

    // 체크박스 Post 관련.
    @FormUrlEncoded
    @POST("test5/DutyExample/update_check.php")
    Call<DutyStep> updateCheck(
            @Field("step_id") Integer step_id,
            @Field("step_check") Integer step_check
    );
}

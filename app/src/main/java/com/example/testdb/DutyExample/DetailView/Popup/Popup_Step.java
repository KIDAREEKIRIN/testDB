package com.example.testdb.DutyExample.DetailView.Popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyStep;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup_Step extends AppCompatActivity {

    EditText et_insertStep;
    Button btn_insertStep, btn_backToDetail_Step;

    Integer title_id;
    String step_name;

    List<DutyStep> dutyStepList;

    private static String TAG = "클릭 시";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_step);

        // Intent 값 받기.
        Intent intent = getIntent();
        title_id = intent.getIntExtra("title_id",0);

        et_insertStep = findViewById(R.id.et_insertStep); // 업무 단계 추가.
        btn_insertStep = findViewById(R.id.btn_insertStep); // 업무 추가하기 버튼.
        btn_backToDetail_Step = findViewById(R.id.btn_backToDetail_Step); // 돌아가기.

        // 업무 단계 추가하기 버튼 클릭.
        btn_insertStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getAllSteps(); // 업무 단계를 다 불러와서,
                step_name = et_insertStep.getText().toString();
                Log.d(TAG, "추가한 데이터 : " + step_name);
                insertStep(step_name, title_id); // DB 에 Insert 완료.
                finish(); // 종료.
            }
        });

        btn_backToDetail_Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void insertStep(String step_name, Integer title_id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyStep> call = service.insertStep(step_name, title_id);

        call.enqueue(new Callback<DutyStep>() {
            @Override
            public void onResponse(Call<DutyStep> call, Response<DutyStep> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success) {
                        Toast.makeText(getApplicationContext(), "업무 단계 추가 성공!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "서버와 통신했으나 메세지 못 불러옴." +response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DutyStep> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "실패한 이유: " + t.getLocalizedMessage());
            }
        });
    }

    private void getAllSteps() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyStep>> call = service.getAllDutySteps();

        call.enqueue(new Callback<List<DutyStep>>() {
            @Override
            public void onResponse(Call<List<DutyStep>> call, Response<List<DutyStep>> response) {
                dutyStepList = response.body();
            }

            @Override
            public void onFailure(Call<List<DutyStep>> call, Throwable t) {
                Log.d(TAG, "실패한 이유 : " + t.getLocalizedMessage());
            }
        });
    }
}
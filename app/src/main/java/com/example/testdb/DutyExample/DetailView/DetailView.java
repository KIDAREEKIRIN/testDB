package com.example.testdb.DutyExample.DetailView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.DutyExample.DTO.DutyStep;
import com.example.testdb.DutyExample.DetailView.Popup.Popup;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailView extends AppCompatActivity {

    String duty_name, addTitle;
    Integer duty_id;
    ActionBar actionBar;
    Context context;

    // RecyclerView

    ExtendedFloatingActionButton fab_addTitle;

    List<DutyTitle> dutyTitleList;
    List<DutyStep> dutyStepList;

    private static String TAG = "넘기는 값";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_example_detail_view);

        // Intent 로 값 받기.
        Intent intent = getIntent();
        duty_id = intent.getIntExtra("duty_id",0); // duty_id 받아온 값 넣기.
        duty_name = intent.getStringExtra("duty_name"); // duty_name 받아온 값 넣기.

        actionBar = getSupportActionBar();
        assert actionBar != null; // setTitle 을 넣기 위한 방법.
        actionBar.setTitle(duty_name); // ActionBar 제목에 duty_name(받아온 값) 넣기.

        dutyTitleList = new ArrayList<>(); // List 만들기.

        /* 받아온 duty_id 값. duty_id 1 야영수련활동 2 수학여행*/
            getAllTitles(); // Title 불러오기.
//            getAllSteps();// Step 불러오기.
            addTitle(); // 업무 제목 추가하기
    }
    // 업무 제목 추가하기.
    private void addTitle() {
        fab_addTitle = findViewById(R.id.fab_addTitle);
        fab_addTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Popup.class);
                intent.putExtra("duty_id",duty_id);
                Log.d(TAG, "duty_id 는? : " + duty_id);
                startActivity(intent);
            }
        });
    }

    // 업무 제목 불러오기 Read.
    public void getAllTitles() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyTitle>> call = service.getAllTitles();

        call.enqueue(new Callback<List<DutyTitle>>() {
            @Override
            public void onResponse(Call<List<DutyTitle>> call, Response<List<DutyTitle>> response) {
                dutyTitleList = response.body();
                // 상위 리사이클러뷰 설정.
                RecyclerView rvItem = findViewById(R.id.rv_item);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                ItemAdapter itemAdapter = new ItemAdapter(dutyTitleList);
                rvItem.setLayoutManager(layoutManager);
                rvItem.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(Call<List<DutyTitle>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "다음에 다시 시도해주세요." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getAllSteps() {
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



    @Override
    protected void onResume() {
        super.onResume();
        getAllTitles(); // 업무 제목 불러오기.
//        getAllSteps(); // 업무 단계 불러오기.
//        addTitle(); // 업무 추가하기.
    }
}
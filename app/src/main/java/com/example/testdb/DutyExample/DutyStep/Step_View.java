package com.example.testdb.DutyExample.DutyStep;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.testdb.DutyExample.DTO.DutyStep;
import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.DutyExample.DetailView.Popup.Popup_Step;
import com.example.testdb.DutyExample.DetailView.SubItemAdapter;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Step_View extends AppCompatActivity {

    RecyclerView rv_dutyStep;
    LinearLayoutManager linearLayoutManager;
//    SubItemAdapter subItemAdapter;
    StepView_Adapter stepView_adapter;
    List<DutyStep> dutyStepList;

    ActionBar actionBar;

    ExtendedFloatingActionButton extendFab_addStep;

    // Intent 로 받아온 값.
    Integer title_id;
    String title_name;

    private static String TAG = "클릭하면? ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);

        // Intent 로 받은 값 표기. (DetailView 에서 받은 title_id)
        Intent intent = getIntent();
        title_id = intent.getIntExtra("title_id",0);
        title_name = intent.getStringExtra("title_name");

        actionBar = getSupportActionBar();
        assert actionBar != null; // setTitle 을 넣기 위한 방법.
        actionBar.setTitle(title_name); // ActionBar 에 title_name(받아온 값) 넣기.

        rv_dutyStep = findViewById(R.id.rv_dutyStep);
        linearLayoutManager = new LinearLayoutManager(Step_View.this);

//        dutyStepList = new ArrayList<>();
//
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<List<DutyStep>> call = service.getSteps(title_id);
//
//        call.enqueue(new Callback<List<DutyStep>>() {
//            @Override
//            public void onResponse(Call<List<DutyStep>> call, Response<List<DutyStep>> response) {
//                dutyStepList = response.body();
//
//                stepView_adapter = new StepView_Adapter(dutyStepList);
////                subItemAdapter = new SubItemAdapter(dutyStepList);
//                rv_dutyStep.setLayoutManager(linearLayoutManager);
//                rv_dutyStep.setAdapter(stepView_adapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<DutyStep>> call, Throwable t) {
//
//            }
//        });

        getAllSteps(); // 업무 단계 전부 불러오기.
        addStep(); // 업무 단계 추가하기.

    }

    private void addStep() {
        extendFab_addStep = findViewById(R.id.extendFab_addStep);
        extendFab_addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Popup_Step.class);
                intent.putExtra("title_id",title_id); // title_id 값 넣어서 넘기기.
                startActivity(intent);
            }
        });
    }

    public void getAllSteps() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyStep>> call = service.getSteps(title_id);

        call.enqueue(new Callback<List<DutyStep>>() {
            @Override
            public void onResponse(Call<List<DutyStep>> call, Response<List<DutyStep>> response) {
                dutyStepList = response.body();

                stepView_adapter = new StepView_Adapter(dutyStepList);
                rv_dutyStep.setLayoutManager(linearLayoutManager);
                rv_dutyStep.setAdapter(stepView_adapter);

            }

            @Override
            public void onFailure(Call<List<DutyStep>> call, Throwable t) {

            }
        });

//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<List<DutyStep>> call = service.getAllDutySteps();
//
//        call.enqueue(new Callback<List<DutyStep>>() {
//            @Override
//            public void onResponse(Call<List<DutyStep>> call, Response<List<DutyStep>> response) {
//                dutyStepList = response.body();
//
//                subItemAdapter = new SubItemAdapter(dutyStepList);
//                rv_dutyStep.setLayoutManager(linearLayoutManager);
//                rv_dutyStep.setAdapter(subItemAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<DutyStep>> call, Throwable t) {
//                Log.d(TAG, "실패한 이유 : " + t.getLocalizedMessage());
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllSteps(); // Step 불러오기.
    }
}
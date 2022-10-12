package com.example.testdb.MyDuty.DutySelect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.MyDuty.MyDuty;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDutySelect extends AppCompatActivity {

    private static String TAG = "넘어가는 데이터는? MyDutySelect";

    Integer loginIndex; // 로그인 인덱스.
    String loginNickName; // 로그인 닉네임.

    List<DutyName> dutyNameList; // 업무이름 List;
    RecyclerView rv_myDutySelect;
    Adapter dutySelect_adapter;

    Button btn_dutySelectOk, btn_dutySelectBack;
    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_duty_select);

        // Intent 로 값 받기 -> 로그인 하면 받은 값.
        Intent intent = getIntent();
        loginIndex = intent.getIntExtra("loginIndex",0); // 로그인 인덱스.
        loginNickName = intent.getStringExtra("loginNickName"); // 로그인 닉네임.

        // 로그인 인덱스 + 로그인 닉네임 값 보내기.

        btn_dutySelectOk = findViewById(R.id.btn_dutySelectOk);
        btn_dutySelectBack = findViewById(R.id.btn_dutySelectBack);

        // 확인버튼 클릭 시,
        btn_dutySelectOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 해당 과목 추가하기 (Insert)
//                insertDutyById()
                Intent intent1 = new Intent(getApplicationContext(), MyDuty.class);
                intent1.putExtra("loginIndex",loginIndex);
                intent1.putExtra("loginNickName",loginNickName);
                startActivity(intent1);
                finish();
            }
        });

        // 뒤로가기 버튼 클릭시,
        btn_dutySelectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        dutyNameList = new ArrayList<>(); // 업무이름 생성하기

        // 업무이름 불러오기. -> MyDutySelect 불러오기.
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyName>> call = service.getDutyNames();

        call.enqueue(new Callback<List<DutyName>>() {
            @Override
            public void onResponse(Call<List<DutyName>> call, Response<List<DutyName>> response) {
                dutyNameList = response.body();
                generateDutyNameList(dutyNameList);

            }

            @Override
            public void onFailure(Call<List<DutyName>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "다음에 다시 시도해주세요." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<List<DutyName>> call = service.getAllDutyNames();
//
//        call.enqueue(new Callback<List<DutyName>>() {
//            @Override
//            public void onResponse(Call<List<DutyName>> call, Response<List<DutyName>> response) {
//                dutyNameList = response.body();
//                generateDutyNameList(dutyNameList); // 업무이름 리스트 불러오기.
//            }
//
//            @Override
//            public void onFailure(Call<List<DutyName>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "다음에 다시 시도해주세요." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    public void generateDutyNameList(List<DutyName> dutyNameList) {
        rv_myDutySelect = findViewById(R.id.rv_myDutySelect);
        dutySelect_adapter = new Adapter(this,dutyNameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyDutySelect.this);
        rv_myDutySelect.setLayoutManager(layoutManager);
        rv_myDutySelect.setAdapter(dutySelect_adapter);
    }

}
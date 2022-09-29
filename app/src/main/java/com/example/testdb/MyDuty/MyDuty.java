package com.example.testdb.MyDuty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.MyDuty.DutySelect.MyDutySelect;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDuty extends AppCompatActivity {

    private static String TAG = "클릭하면";

    Integer loginIndex; // 로그인 인덱스
    String loginNickName; // 로그인 닉네임

    List<DutyName> dutyNameList;
    MyDuty_Adapter myDuty_adapter;
    RecyclerView rv_myDuty; // RecyclerView
    // 확장형 추가버튼
    ExtendedFloatingActionButton extendFab_myDutyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myduty);

        // 인텐트 값 받기 -> MainActivity 에서.
        Intent intent = getIntent();
        loginIndex = intent.getIntExtra("loginIndex",0); // 로그인 인덱스 값.
        loginNickName = intent.getStringExtra("loginNickName"); // 로그인 닉네임 값.

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyName>> call = service.getMyDuties(loginIndex);

        call.enqueue(new Callback<List<DutyName>>() {
            @Override
            public void onResponse(Call<List<DutyName>> call, Response<List<DutyName>> response) {
                dutyNameList = response.body();
                generateMyDuties(dutyNameList); // myDuty 불러오기.
            }

            @Override
            public void onFailure(Call<List<DutyName>> call, Throwable t) {

            }
        });


        addMyDuties(); // 나의 업무 추가하기.

    }

    public void generateMyDuties(List<DutyName> dutyNameList) {
        rv_myDuty = findViewById(R.id.rv_myDuty);
        myDuty_adapter = new MyDuty_Adapter(this,dutyNameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyDuty.this);
        rv_myDuty.setLayoutManager(layoutManager);
        rv_myDuty.setAdapter(myDuty_adapter);

    }

    // 업무 추가하기.
    private void addMyDuties() {
        extendFab_myDutyList = findViewById(R.id.extendFab_myDutyList);
        extendFab_myDutyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog();
//                // 업무 추가하기 화면 전환.
//                Intent intent = new Intent(getApplicationContext(), MyDutySelect.class);
//                intent.putExtra("loginIndex",loginIndex); // 로그인 인덱스.
//                intent.putExtra("loginNickName",loginNickName); // 로그인 닉네임.
//                startActivity(intent);

            }
        });
    }

    public void AlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("나의 업무 추가하기");
        builder.setItems(R.array.MyDuty, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos) {
                String[] items = getResources().getStringArray(R.array.MyDuty);
                Toast.makeText(getApplicationContext(), items[pos], Toast.LENGTH_LONG).show();
                // 기존 업무 클릭 시,
                if(pos == 0) {
                    Intent intent = new Intent(getApplicationContext(), MyDutySelect.class);
                    intent.putExtra("loginIndex",loginIndex); // 로그인 인덱스.
                    intent.putExtra("loginNickName",loginNickName); // 로그인 닉네임.
                    Log.d(TAG, "로그인 인덱스: : " + loginIndex);
                    Log.d(TAG, "로그인 닉네임: + " + loginNickName);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
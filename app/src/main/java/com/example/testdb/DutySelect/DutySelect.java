package com.example.testdb.DutySelect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testdb.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class DutySelect extends AppCompatActivity {

    Integer loginIndex;
    String loginNickName;

    RecyclerView rv_myDutySelect;
    ExtendedFloatingActionButton extendFab_myDutyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutyselect);

        // 인텐트 값 받기 -> MainActivity 에서.
        Intent intent = getIntent();
        loginIndex = intent.getIntExtra("loginIndex",0); // 로그인 인덱스 값.
        loginNickName = intent.getStringExtra("loginNickName"); // 로그인 닉네임 값.

        addDuties();

    }

    private void addDuties() {
        extendFab_myDutyList = findViewById(R.id.extendFab_myDutyList);
        extendFab_myDutyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
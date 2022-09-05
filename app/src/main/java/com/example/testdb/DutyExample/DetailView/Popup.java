package com.example.testdb.DutyExample.DetailView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testdb.R;

public class Popup extends AppCompatActivity {

    EditText et_insertTitle;
    Button btn_insertTitle, btn_backToDetail;

    String addTitle;
    Integer duty_id;

    private static String TAG = "추가한 데이터는? ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Intent intent = getIntent();
        duty_id = intent.getIntExtra("duty_id",0); // duty_id 값 받아오기.


        et_insertTitle = findViewById(R.id.et_insertTitle); // 추가할 내용.
        btn_insertTitle = findViewById(R.id.btn_insertTitle); // 확인 버튼.
        btn_backToDetail = findViewById(R.id.btn_backToDetail); // 추가 버튼.


        btn_insertTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTitle = et_insertTitle.getText().toString();
                Intent intent = new Intent(getApplicationContext(),DetailView.class);
                // EditText 값 + title_order + duty_id 값.
                intent.putExtra("addTitle",addTitle); // editText 에 입력한 값
                Log.d(TAG, "추가한 데이터 : " + addTitle);
                startActivity(intent);

            }
        });

    }
}
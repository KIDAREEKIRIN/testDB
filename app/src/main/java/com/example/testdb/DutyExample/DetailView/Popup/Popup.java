package com.example.testdb.DutyExample.DetailView.Popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.DutyExample.DetailView.DetailView;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup extends AppCompatActivity {

    EditText et_insertTitle;
    Button btn_insertTitle, btn_backToDetail;

    String title_name;
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

        // 확인 버튼 클릭 시,
        btn_insertTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_name = et_insertTitle.getText().toString(); // 업무 제목
                Log.d(TAG, "추가한 데이터 : " + title_name);
                insertTitle(title_name,duty_id); // Retrofit2 을 통한 title_name, duty_id 추가
                finish(); // 끝내기
            }
        });

        btn_backToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    // 업무 제목 title_name + duty_id 추가하기
    private void insertTitle(String title_name, Integer duty_id) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyTitle> call = service.insertDutyTitle(title_name, duty_id);

        call.enqueue(new Callback<DutyTitle>() {
            @Override
            public void onResponse(Call<DutyTitle> call, Response<DutyTitle> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success) {
                        Toast.makeText(getApplicationContext(), "업무 제목 추가 성공!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "서버와 통신했으나 메세지 못 불러옴." +response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DutyTitle> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
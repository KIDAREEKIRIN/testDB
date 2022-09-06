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
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup_Edit extends AppCompatActivity {

    EditText et_editTitle;
    Button btn_editTitle, btn_backToDetail_edit;

    String title_name;
    Integer duty_id, title_id;

    private static String TAG = "클릭 하면";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_edit);

        // 해당 id 선언하기.
        et_editTitle = findViewById(R.id.et_editTitle); // 수정할 제목,
        btn_editTitle = findViewById(R.id.btn_editTitle); // 수정하기 버튼.
        btn_backToDetail_edit = findViewById(R.id.btn_backToDetail_edit); // 뒤로가기.

        // Intent 로 값 받아오기.
        Intent intent = getIntent();
        title_name = intent.getStringExtra("title_name"); // 업무 제목
        duty_id = intent.getIntExtra("duty_id",0); // duty_id
        title_id = intent.getIntExtra("title_id",0); // title_id

        et_editTitle.setText(title_name); // EditText 에 받아온 String title_name 표시하기.


        // editTitle(수정하기 버튼 클릭)
        btn_editTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTitle = et_editTitle.getText().toString();
                editTitle(title_id, editTitle); // 수정하기 내용.
                Log.d(TAG, "수정하기 버튼 클릭 시 넘어간 데이터: title_id " + title_id);
                Log.d(TAG, "수정하기 버튼 클릭 시 넘어간 데이터: editTitle " + editTitle);
                finish(); // 끝내기.
            }
        });

        // 뒤로가기.
        btn_backToDetail_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void editTitle(Integer title_id, String title_name) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyTitle> call = service.editDutyTitle(title_id, title_name);

        call.enqueue(new Callback<DutyTitle>() {
            @Override
            public void onResponse(Call<DutyTitle> call, Response<DutyTitle> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success) {
                        Toast.makeText(getApplicationContext(), "업무 제목 수정 완료.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "서버와 통신했으나 추가 안됨.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DutyTitle> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통신 실패", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "실패한 이유: " + t.getLocalizedMessage());
            }
        });
    }
}
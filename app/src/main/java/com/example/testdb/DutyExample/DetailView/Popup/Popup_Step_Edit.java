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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup_Step_Edit extends AppCompatActivity {

    Integer step_id, nowPos;
    String step_name;

    EditText et_editStep;
    Button btn_editStep,btn_backToDetail_editStep;

    private static String TAG = "클릭 하면";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_step_edit);

        et_editStep = findViewById(R.id.et_editStep);
        btn_editStep = findViewById(R.id.btn_editStep);
        btn_backToDetail_editStep = findViewById(R.id.btn_backToDetail_editStep);

        // Intent 로 넘어온 값 받기.
        Intent intent = getIntent();
        nowPos = intent.getIntExtra("nowPos",0); // 현재 포지션 값.
        step_id = intent.getIntExtra("step_id",0); // step_id
        step_name = intent.getStringExtra("step_name"); // step_name

        et_editStep.setText(step_name);

        // 수정 버튼 클릭시,
        btn_editStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editStep = et_editStep.getText().toString();
                editStep(step_id, editStep); // 수정할 duty_step name.
                finish();
            }
        });
        // 취소 버튼 클릭 시,
        btn_backToDetail_editStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void editStep(Integer step_id, String step_name) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyStep> call = service.updateStep(step_id, step_name);

        call.enqueue(new Callback<DutyStep>() {
            @Override
            public void onResponse(Call<DutyStep> call, Response<DutyStep> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success) {
                        Log.d(TAG, "통신 + 불러오기 성공 " + "수정완료");
                        Toast.makeText(getApplicationContext(), "업무 단계 수정 완료", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "수정 불가.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DutyStep> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요/", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: 실패한 이유" + t.getLocalizedMessage());
            }
        });
    }
}
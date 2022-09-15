package com.example.testdb.DutyExample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DutyExample extends AppCompatActivity {

    List<DutyName> dutyNameList; // 업무이름 List.
    SearchView sv_dutyExample; // 검색.
    RecyclerView rv_dutyExample; // RecyclerView

    Adapter example_adapter; // 어댑터.

    Button btn_allCate, btn_gyomoo, btn_chehum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_example);

        // DutyName 받아오기.
        dutyNameList = new ArrayList<>();

        getAllDuties(); // Get 업무 이름 데이터
        searchView(); // SearchView 검색.
        buttonCategory(); // Button 카테고리 누르기.
        // Retrofit 을 통한 데이터 Get.
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyName>> call = service.getAllDutyNames();

        call.enqueue(new Callback<List<DutyName>>() {
            @Override
            public void onResponse(Call<List<DutyName>> call, Response<List<DutyName>> response) {
                dutyNameList = response.body();
                generateDataList(dutyNameList);
            }

            @Override
            public void onFailure(Call<List<DutyName>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "다음에 다시 시도해주세요." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void buttonCategory() {
        btn_allCate = findViewById(R.id.btn_allCate); // 전체업무.
        btn_chehum = findViewById(R.id.btn_chehum); // 체험업무.
        btn_chehum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_gyomoo = findViewById(R.id.btn_gyomoo); // 교무업무.
    }

    private void searchView() {
        sv_dutyExample = findViewById(R.id.sv_dutyExample);

        sv_dutyExample.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void getAllDuties() {
        // Retrofit 을 통한 데이터 Get.
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyName>> call = service.getAllDutyNames();

        call.enqueue(new Callback<List<DutyName>>() {
            @Override
            public void onResponse(Call<List<DutyName>> call, Response<List<DutyName>> response) {
                dutyNameList = response.body();

            }

            @Override
            public void onFailure(Call<List<DutyName>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "다음에 다시 시도해주세요." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<DutyName> dutyNameList) {
        rv_dutyExample = findViewById(R.id.rv_dutyExample);
        example_adapter = new Adapter(this,dutyNameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DutyExample.this);
        rv_dutyExample.setLayoutManager(layoutManager);
        rv_dutyExample.setAdapter(example_adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filter(String newText) {
        List<DutyName> filteredList = new ArrayList<>();
        for (DutyName item : dutyNameList) {
            if(item.getDuty_name().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        example_adapter.filterList(filteredList);
        example_adapter.notifyDataSetChanged();
    }
}
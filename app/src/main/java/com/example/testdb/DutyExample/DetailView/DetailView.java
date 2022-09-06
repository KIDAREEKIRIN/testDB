package com.example.testdb.DutyExample.DetailView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.DutyExample.DTO.SubItem;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailView extends AppCompatActivity {

    String duty_name, addTitle;
    Integer duty_id;
    ActionBar actionBar;

    ExtendedFloatingActionButton fab_addTitle;

    List<DutyTitle> dutyTitleList;

    private static String TAG = "넘기는 값";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_example_detail_view);

        // Intent 로 값 받기.
        Intent intent = getIntent();
        duty_id = intent.getIntExtra("duty_id",0); // duty_id 받아온 값 넣기.
        duty_name = intent.getStringExtra("duty_name"); // duty_name 받아온 값 넣기.
        addTitle = intent.getStringExtra("addTitle"); // Popup 창에서 추가한 업무 제목.

        actionBar = getSupportActionBar();
        assert actionBar != null; // setTitle 을 넣기 위한 방법.
        actionBar.setTitle(duty_name); // ActionBar 제목에 duty_name(받아온 값) 넣기.

        dutyTitleList = new ArrayList<>(); // List 만들기.

        getAllTitles(); // Title 불러오기.
        addTitle(); // 업무 제목 추가하기
        editTitle(); // 업무 제목 수정하기.

    }

    private void editTitle() {

    }

    // 업무 제목 추가하기.
    private void addTitle() {
        fab_addTitle = findViewById(R.id.fab_addTitle);
        fab_addTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Popup.class);
                intent.putExtra("duty_id",duty_id);
                Log.d(TAG, "duty_id 는? : " + duty_id);
                startActivity(intent);
//                intent.putExtra("duty_id")
            }
        });
    }

    private void getAllTitles() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyTitle>> call = service.getAllTitles();

        call.enqueue(new Callback<List<DutyTitle>>() {
            @Override
            public void onResponse(Call<List<DutyTitle>> call, Response<List<DutyTitle>> response) {
                dutyTitleList = response.body();
                Double_RecyclerView(response.body());
            }

            @Override
            public void onFailure(Call<List<DutyTitle>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "다음에 다시 시도해주세요." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Double_RecyclerView(List<DutyTitle> dutyTitleList) {
        // 상위 리사이클러뷰 설정.
        RecyclerView rvItem = findViewById(R.id.rv_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailView.this);
        ItemAdapter itemAdapter = new ItemAdapter(dutyTitleList);
        rvItem.setLayoutManager(layoutManager);
        rvItem.setAdapter(itemAdapter);
    }

    // 상위아이템 큰박스 아이템을 10개 만듭니다.
//    private List<DutyTitle> buildItemList() {
//        List<DutyTitle> itemList = new ArrayList<>();
//        for (int i=0; i<10; i++) {
//            DutyTitle dutyTitle = new DutyTitle("Item "+i, buildSubItemList()); // 아이템 제목, SubItem List.
//            itemList.add(item);
//        }
//        return itemList;
//    }

    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
    public List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            SubItem subItem = new SubItem("Sub Item "+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }

}
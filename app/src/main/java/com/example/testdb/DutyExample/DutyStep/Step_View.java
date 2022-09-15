package com.example.testdb.DutyExample.DutyStep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
    StepView_Adapter stepView_adapter; // Adapter
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

        // ItemTouchHelper 관련.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END ) {
            // 드래그 앤 드롭.
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int nowPos = viewHolder.getAdapterPosition();
//                stepView_adapter.insertStepOrder(nowPos);
                // dutyStepList 불러와서 step_id에 해당하는 step_order 수정하기.
                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<List<DutyStep>> call = service.getSteps(title_id);

                call.enqueue(new Callback<List<DutyStep>>() {
                    @Override
                    public void onResponse(Call<List<DutyStep>> call, Response<List<DutyStep>> response) {
                        dutyStepList = response.body();
                        stepView_adapter.updateStepOrder(dutyStepList.get(nowPos).getStep_id(),nowPos);
                    }

                    @Override
                    public void onFailure(Call<List<DutyStep>> call, Throwable t) {

                    }
                });

                Log.d(TAG, "onMove: 현재 포지션: " + viewHolder.getAdapterPosition());
                Log.d(TAG, "onMove: 옮기는 포지션: " + target.getAdapterPosition());
                return stepView_adapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition()); // 처음위치 -> 옮겨질 위치.

            }

            // 삭제하기.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                stepView_adapter.remove(viewHolder.getAdapterPosition());
            }

            // 선택했을 때,
            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if(actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY); // 선택한 viewHolder 의 색깔이 라이트그레이.
                }
            }

            // 다시 돌아갔을 때,
            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE); // 선택한 viewHolder 값이 풀리면 화이트로 돌아감.(기존 화이트)
            }
        });
        itemTouchHelper.attachToRecyclerView(rv_dutyStep); // RecyclerView 에 붙여주기.

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

    // 업무 단계 전부 불러오기.
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllSteps(); // Step 불러오기.
    }
}
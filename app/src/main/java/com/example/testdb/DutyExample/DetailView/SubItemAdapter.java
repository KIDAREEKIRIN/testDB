package com.example.testdb.DutyExample.DetailView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyStep;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    List<DutyStep> dutyStepList;

    private static String TAG = "클릭하면";

    public SubItemAdapter(List<DutyStep> dutyStepList) {
        this.dutyStepList = dutyStepList;
    }

    public class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubItemTitle;

        SubItemViewHolder(View itemView) {
            super(itemView);

            tvSubItemTitle = itemView.findViewById(R.id.tv_sub_item_title);
        }
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        DutyStep dutyStep = dutyStepList.get(i);
        subItemViewHolder.tvSubItemTitle.setText(dutyStep.getStep_name());// step_name 붙이기.

//        dutyStepList = new ArrayList<>();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<DutyStep>> call = service.getAllDutySteps();

        call.enqueue(new Callback<List<DutyStep>>() {
            @Override
            public void onResponse(Call<List<DutyStep>> call, Response<List<DutyStep>> response) {
                dutyStepList = response.body();
            }

            @Override
            public void onFailure(Call<List<DutyStep>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dutyStepList.size();
    }


}

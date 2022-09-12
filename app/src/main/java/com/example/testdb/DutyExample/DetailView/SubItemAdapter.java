package com.example.testdb.DutyExample.DetailView;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
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

    List<DutyStep> dutyStepList; // 업무 제목.
    String step_name;
    Integer step_id, step_check;

    private static String TAG = "클릭하면";

    public SubItemAdapter(List<DutyStep> dutyStepList) {
        this.dutyStepList = dutyStepList;
    }

    public class SubItemViewHolder extends RecyclerView.ViewHolder {

        CheckedTextView ct_duty_step;

        SubItemViewHolder(View itemView) {
            super(itemView);

            ct_duty_step = itemView.findViewById(R.id.ct_sub_item);
        }
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_check_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder holder, int i) {
        DutyStep dutyStep = dutyStepList.get(i);
        step_name = dutyStep.getStep_name(); // step_name 변수.
        step_id = dutyStep.getStep_id(); // step_id 변수
        step_check = dutyStep.getStep_check(); // step_check 변수
        holder.ct_duty_step.setText(step_name);// step_name 붙이기

        holder.ct_duty_step.setTag(dutyStep.getStep_check());

        if(step_check == 1) {
            holder.ct_duty_step.toggle();
            holder.ct_duty_step.isChecked();
            holder.ct_duty_step.setPaintFlags(holder.ct_duty_step.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // CheckedTextView 클릭 시,
            holder.ct_duty_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ct_duty_step.toggle();
                    // CheckBox 체크 되면.
                    if(holder.ct_duty_step.isChecked()) {
                        holder.ct_duty_step.setPaintFlags(holder.ct_duty_step.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        updateCheck(step_id, step_check);
                    } else {
                        holder.ct_duty_step.setPaintFlags(0);
                        updateCheck(step_id, step_check-1);
                    }
                }
            });
            // 체크 안되어 있으면,
        } else {
            holder.ct_duty_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ct_duty_step.toggle();
//                    int check = duty
                    if(holder.ct_duty_step.isChecked()) {
                        holder.ct_duty_step.setPaintFlags(holder.ct_duty_step.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        updateCheck(step_id,step_check+1);
                    } else {
                        holder.ct_duty_step.setPaintFlags(0);
                        updateCheck(step_id,step_check);
                    }
                }
            });
        }


    }

    // CheckedTextView Retrofit 통신.
    private void updateCheck(Integer step_id, Integer step_check) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyStep> call = service.updateCheck(step_id, step_check);

        call.enqueue(new Callback<DutyStep>() {
            @Override
            public void onResponse(Call<DutyStep> call, Response<DutyStep> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "정상 작동 : " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DutyStep> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dutyStepList.size();
    }


}

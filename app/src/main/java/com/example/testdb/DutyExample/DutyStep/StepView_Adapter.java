package com.example.testdb.DutyExample.DutyStep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyStep;
import com.example.testdb.R;

import java.util.List;

public class StepView_Adapter extends RecyclerView.Adapter<StepView_Adapter.StepViewHolder> {

    List<DutyStep> dutyStepList;

    public StepView_Adapter(List<DutyStep> dutyStepList) {
        this.dutyStepList = dutyStepList;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder{

        TextView tv_dutyStep;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_dutyStep = itemView.findViewById(R.id.tv_sub_item_title);
        }
    }


    @NonNull
    @Override
    public StepView_Adapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_item,parent,false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepView_Adapter.StepViewHolder holder, int position) {
        DutyStep dutyStep = dutyStepList.get(position);
        holder.tv_dutyStep.setText(dutyStep.getStep_name());

    }

    @Override
    public int getItemCount() {
        return dutyStepList.size();
    }

}

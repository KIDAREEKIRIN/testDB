package com.example.testdb.DutyExample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.R;

import java.util.List;

public class DutyExample_Adapter extends RecyclerView.Adapter<DutyExample_Adapter.ExampleViewHolder> {

    Context context;
    List<DutyName> dutyNameList;
    ExampleClickListener mListener = null;

    public DutyExample_Adapter(Context context, List<DutyName> dutyNameList) {
        this.context = context;
        this.dutyNameList = dutyNameList;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView tv_dutyExampleName;
        CardView cv_dutyExampleName;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_dutyExampleName = itemView.findViewById(R.id.tv_dutyExampleName);
            cv_dutyExampleName = itemView.findViewById(R.id.cv_dutyExampleName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if ( pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        // 해당 클릭 포지션의 dutyName 담아서 보내기.
                        Intent intent = new Intent(context,DutyExample_DetailView.class);
                        intent.putExtra("duty_name", dutyNameList.get(pos).getDuty_name());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public DutyExample_Adapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.example_items,parent,false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DutyExample_Adapter.ExampleViewHolder holder, int position) {
        holder.tv_dutyExampleName.setText(dutyNameList.get(position).getDuty_name()); // TextView 에 duty_name 을 붙임.
        holder.cv_dutyExampleName.setTag(dutyNameList.get(position).getCate_id()); // CardView 에 카테고리 id 를 붙임

    }

    @Override
    public int getItemCount() {
        return dutyNameList.size();
    }

    public void filterList(List<DutyName> filteredList) {
        dutyNameList = filteredList;
        notifyDataSetChanged();
    }


}

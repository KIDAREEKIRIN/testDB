package com.example.testdb.MyDuty.DutySelect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyName;
import com.example.testdb.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<DutyName> dutyNameList;

    String duty_name;
    Integer duty_id;

    LayoutInflater layoutInflater;

    private static String TAG = "데이터 궁금하지? duty_select";

    public Adapter(Context context, List<DutyName> dutyNameList) {
        this.context = context;
        this.dutyNameList = dutyNameList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv_dutySelectName;
        CheckedTextView ct_myDutySelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_dutySelectName = itemView.findViewById(R.id.cv_dutySelectName); // 카드뷰.
            ct_myDutySelect = itemView.findViewById(R.id.ct_myDutySelect); // 체크박스+텍스트뷰.


        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dutyselect_items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.cv_dutySelectName.setTag(dutyNameList.get(position).getDuty_id());
        holder.ct_myDutySelect.setText(dutyNameList.get(position).getDuty_name());



    }

    @Override
    public int getItemCount() {
        return dutyNameList.size();
    }


}

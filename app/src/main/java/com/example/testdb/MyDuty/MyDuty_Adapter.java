package com.example.testdb.MyDuty;

import android.content.Context;
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

public class MyDuty_Adapter extends RecyclerView.Adapter<MyDuty_Adapter.ViewHolder> {

    Context context;
    List<DutyName> dutyNameList;

    private static String TAG = "데이터 궁금하면? + MyDuty_Adapter";

    LayoutInflater layoutInflater;

    public MyDuty_Adapter(Context context, List<DutyName> dutyNameList) {
        this.context = context;
        this.dutyNameList = dutyNameList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cv_myDutyName; // 카드뷰.
        TextView tv_myDutyName;
//        CheckedTextView ct_myDutyName; // 체크박스 + TextView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_myDutyName = itemView.findViewById(R.id.cv_myDutyName);
//            ct_myDutyName = itemView.findViewById(R.id.ct_myDutyName);
            tv_myDutyName = itemView.findViewById(R.id.tv_myDutyName);
        }
    }

    @NonNull
    @Override
    public MyDuty_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.myduty_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDuty_Adapter.ViewHolder holder, int position) {
        holder.cv_myDutyName.setTag(dutyNameList.get(position).getCate_id()); // 카테고리 아이디 붙이기.
        holder.tv_myDutyName.setText(dutyNameList.get(position).getDuty_name()); // dutyName 붙이기.
    }

    @Override
    public int getItemCount() {
        return dutyNameList.size();
    }

}

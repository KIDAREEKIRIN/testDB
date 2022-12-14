package com.example.testdb.MyDuty.DutySelect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
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
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<DutyName> dutyNameList;

    String duty_name;
    Integer duty_id, name_check;

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
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DutyName dutyName = dutyNameList.get(position);
        duty_id = dutyNameList.get(position).getDuty_id(); // duty_name 인덱스.
        duty_name = dutyNameList.get(position).getDuty_name(); // duty_name
        name_check = dutyNameList.get(position).getName_check(); // 체크박스 유/무

        holder.cv_dutySelectName.setTag(duty_id);
//        holder.ct_myDutySelect.setTag(name_check);
        // CheckedTextView 클릭한다면,

        // 여기에 문제가 있다, -> 22.10.12.(수) -> 체크된 데이터가 넘어가지 않음.
        // 체크되어 있으면,
        if(dutyName.getName_check() == 1) {
            holder.ct_myDutySelect.toggle();
            holder.ct_myDutySelect.isChecked();
            holder.ct_myDutySelect.setPaintFlags(holder.ct_myDutySelect.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // View.INVISIBLE 은 자리는 차지하고 해당 내용은 보이지 않음.
//            holder.ct_myDutySelect.setVisibility(View.GONE); // 자리에서 사라지고, 밑의 부분이 올라옴.
            holder.ct_myDutySelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ct_myDutySelect.toggle();
                    int check = dutyName.getName_check();
                    // check 되면
                    if(holder.ct_myDutySelect.isChecked()) {
                        holder.ct_myDutySelect.setPaintFlags(holder.ct_myDutySelect.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        // 체크된 값을 보내기
                        updateNameCheck(dutyName.getDuty_id(),check);
                        // 체크된 값이 없으면,
                    } else {
                        holder.ct_myDutySelect.setPaintFlags(0);
                        updateNameCheck(dutyName.getDuty_id(),check-1);
                    }
                }
            });
            // check 안되어 있으면,
        } else {
            holder.ct_myDutySelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ct_myDutySelect.toggle();
                    int check = dutyName.getName_check();
                    if(holder.ct_myDutySelect.isChecked()) {
                        holder.ct_myDutySelect.setPaintFlags(holder.ct_myDutySelect.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        updateNameCheck(dutyName.getDuty_id(), check+1);
                    } else {
                        holder.ct_myDutySelect.setPaintFlags(0);
                        updateNameCheck(dutyName.getDuty_id(), check);
                    }
                }
            });
        }


        holder.ct_myDutySelect.setText(duty_name); // 업무 이름 붙이기. -> subItem과 순서가 다름.

    }

    @Override
    public int getItemCount() {
        return dutyNameList.size();
    }

    public void updateNameCheck(Integer duty_id, Integer name_check) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyName> call = service.updateNameCheck(duty_id, name_check);

        call.enqueue(new Callback<DutyName>() {
            @Override
            public void onResponse(Call<DutyName> call, Response<DutyName> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "정상 작동 : "+ response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DutyName> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

}

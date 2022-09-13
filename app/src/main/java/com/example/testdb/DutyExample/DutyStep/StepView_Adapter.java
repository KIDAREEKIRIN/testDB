package com.example.testdb.DutyExample.DutyStep;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyStep;
import com.example.testdb.DutyExample.DetailView.Popup.Popup_Step_Edit;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepView_Adapter extends RecyclerView.Adapter<StepView_Adapter.StepViewHolder> {

    List<DutyStep> dutyStepList;

    Integer step_id;
    String step_name;

    private static String TAG = "클릭";

    public StepView_Adapter(List<DutyStep> dutyStepList) {
        this.dutyStepList = dutyStepList;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder{

        TextView tv_dutyStep;
        ImageButton ib_deleteStep;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_dutyStep = itemView.findViewById(R.id.tv_sub_item_title);
            ib_deleteStep = itemView.findViewById(R.id.ib_deleteStep);

            // TextView 클릭하면,
            tv_dutyStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        step_id = dutyStepList.get(pos).getStep_id(); // step_id
                        step_name = dutyStepList.get(pos).getStep_name(); // step_name
                        Log.d(TAG, "현재 포지션: "+ pos);
                        Log.d(TAG, "현재 step_id: " + step_id);
                        Log.d(TAG, "현재 step_name: " + step_name);

                        // 수정하는 Popup_Step_Edit 으로 보내기.
                        Intent intent  = new Intent(view.getContext(), Popup_Step_Edit.class);
                        intent.putExtra("nowPos",pos); // 현재 포지션
                        intent.putExtra("step_id",step_id); // step_id
                        intent.putExtra("step_name",step_name); // step_name
                        view.getContext().startActivity(intent);
                    }
                }
            });
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
        holder.tv_dutyStep.setText(dutyStep.getStep_name());// 업무 단계 이름.
        holder.ib_deleteStep.setTag(dutyStep.getStep_id()); // 삭제하기(이미지버튼)

        // 삭제하기 이미지버튼 클릭 시,
        holder.ib_deleteStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("업무 단계 삭제").setMessage("정말 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Integer step_id = dutyStep.getStep_id();
                        remove(holder.getAdapterPosition());
                        deleteStep(step_id);
                        notifyDataSetChanged();
                        dialogInterface.dismiss(); // 다이얼로그 창 닫기.
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dutyStepList.size();
    }

    //Retrofit 에서 duty_step 삭제하기.
    private void deleteStep(Integer step_id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyStep> call = service.deleteStep(step_id);

        call.enqueue(new Callback<DutyStep>() {
            @Override
            public void onResponse(Call<DutyStep> call, Response<DutyStep> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success) {
                        Log.d(TAG, "onResponse: 통신 + 삭제완료");
                    } else {
                        Log.d(TAG, "onResponse: 응답은 했으나 삭제는 불가능.");
                    }
                }
            }

            @Override
            public void onFailure(Call<DutyStep> call, Throwable t) {
                Log.d(TAG, "onFailure: 실패한 이유" + t.getLocalizedMessage());
            }
        });
    }

    // dutyStepList 에서 지우기.
    private void remove(int position) {
        try {
            dutyStepList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

}

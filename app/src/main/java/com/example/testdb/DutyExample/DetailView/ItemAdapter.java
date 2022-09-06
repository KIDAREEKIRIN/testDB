package com.example.testdb.DutyExample.DetailView;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.DutyExample.DTO.SubItem;
import com.example.testdb.DutyExample.DetailView.Popup.Popup;
import com.example.testdb.DutyExample.DetailView.Popup.Popup_Edit;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<DutyTitle> dutyTitleList;
//    Context context;
    private static String TAG = "현재 클릭";

    public ItemAdapter(List<DutyTitle> dutyTitleList) {
        this.dutyTitleList = dutyTitleList;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemTitle;
        private RecyclerView rvSubItem;
        private ImageButton ib_deleteTitle;

        ItemViewHolder(View itemView) {
            super(itemView);
            // 부모 타이틀
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            // 자식아이템 영역
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            // 삭제 이미지버튼
            ib_deleteTitle = itemView.findViewById(R.id.ib_deleteTitle);

            // TextView 클릭 시,
            tvItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        String title_name = dutyTitleList.get(pos).getTitle_name();
                        Integer duty_id = dutyTitleList.get(pos).getDuty_id();
                        Integer title_id = dutyTitleList.get(pos).getTitle_id();

                        Log.d(TAG, "현재 포지션 : " + pos);
                        Log.d(TAG, "현재 title_name : " + title_name);
                        Log.d(TAG, "현재 duty_id : " + duty_id);

                        // 수정하는 Popup_Edit 으로 보내서, title_name, duty_id 값을 같이 보내기.
                        Intent intent = new Intent(view.getContext(), Popup_Edit.class);
                        intent.putExtra("title_name",title_name); // title_name 값을 담아서 보내기.
                        intent.putExtra("duty_id",duty_id); // duty_id 값을 담아서 보내기.
                        intent.putExtra("title_id",title_id); // title_id 값을 담아서 보내기.
                        view.getContext().startActivity(intent);
                    }
                }
            });

//            ib_deleteTitle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int nowPos = getAdapterPosition();
//                    if(nowPos != RecyclerView.NO_POSITION) {
//
//                    }
//                }
//            });
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        DutyTitle dutyTitle = dutyTitleList.get(i);
        itemViewHolder.tvItemTitle.setText(dutyTitle.getTitle_name()); // 업무 제목 붙이기.
        itemViewHolder.ib_deleteTitle.setTag(dutyTitle.getTitle_id()); // 이미지 버튼에 업무 title_id 붙이기.

        itemViewHolder.ib_deleteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("업무 제목 삭제").setMessage("정말 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Integer title_id = dutyTitle.getTitle_id();
                        remove(itemViewHolder.getAdapterPosition());
                        deleteTitle(title_id);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rvSubItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        // 자식 어답터 설정
        SubItemAdapter subItemAdapter = new SubItemAdapter(buildSubItemList());

        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
    }

    @Override
    public int getItemCount() {
        return dutyTitleList.size();
    }

    private void deleteTitle(Integer title_id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DutyTitle> call = service.deleteTitle(title_id);

        call.enqueue(new Callback<DutyTitle>() {
            @Override
            public void onResponse(Call<DutyTitle> call, Response<DutyTitle> response) {
                if(response.isSuccessful() && response.body() != null) {
                    boolean success = response.body().getSuccess();
                    if(success) {
//                        Toast.makeText(view.getContext(), "통신 + 삭제완료.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "응답하면? = 통신 + 삭제 완료. ");
                    } else {
//                        Toast.makeText(view.getContext(), "통신만 됨.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "응답은 했으나 삭제는 불가능.");
                    }
                }
            }

            @Override
            public void onFailure(Call<DutyTitle> call, Throwable t) {
//                Toast.makeText(view.getContext(), "통신불가", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "실패한 이유 : " + t.getLocalizedMessage());
            }
        });
    }

    // dutyTitleList 에서 지우기.
    private void remove(int position) {
        try{
          dutyTitleList.remove(position);
          notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            SubItem subItem = new SubItem("Sub Item "+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }


}

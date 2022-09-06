package com.example.testdb.DutyExample.DetailView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.DutyExample.DTO.SubItem;
import com.example.testdb.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<DutyTitle> dutyTitleList;
    Context context;
    private static String TAG = "현재 클릭";

    public ItemAdapter(List<DutyTitle> dutyTitleList) {
        this.dutyTitleList = dutyTitleList;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemTitle;
        private RecyclerView rvSubItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            // 부모 타이틀
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            // 자식아이템 영역
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);

            // TextView 클릭 시,
            tvItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        String title_name = dutyTitleList.get(pos).getTitle_name();
                        Integer duty_id = dutyTitleList.get(pos).getDuty_id();

                        Log.d(TAG, "현재 포지션 : " + pos);
                        Log.d(TAG, "현재 title_name : " + title_name);
                        Log.d(TAG, "현재 duty_id : " + duty_id);

                        Intent intent = new Intent(context.getApplicationContext(),Popup.class);
                        intent.putExtra("title_name",title_name); // title_name 값을 담아서 보내기.
                        intent.putExtra("duty_id",duty_id); // duty_id 값을 담아서 보내기.
                        context.startActivity(intent);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nowPos = getAdapterPosition();
                    if(nowPos != RecyclerView.NO_POSITION) {

                    }
                }
            });
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
        itemViewHolder.tvItemTitle.setText(dutyTitle.getTitle_name());

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

    public List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            SubItem subItem = new SubItem("Sub Item "+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }


}

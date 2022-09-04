package com.example.testdb.DutyExample.DetailView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.DutyExample.DTO.DutyTitle;
import com.example.testdb.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<DutyTitle> dutyTitleList;

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
        SubItemAdapter subItemAdapter = new SubItemAdapter(dutyTitle.getSubItemList());

        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
    }

    @Override
    public int getItemCount() {
        return dutyTitleList.size();
    }


}

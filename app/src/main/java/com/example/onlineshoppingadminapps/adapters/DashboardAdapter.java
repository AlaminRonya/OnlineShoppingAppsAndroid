package com.example.onlineshoppingadminapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingadminapps.DashboardFragment;
import com.example.onlineshoppingadminapps.callbacks.OnQueryItemSelectedCompletedListener;
import com.example.onlineshoppingadminapps.databinding.DashboardItemRowBinding;
import com.example.onlineshoppingadminapps.models.DashboardItem;
import com.example.onlineshoppingadminapps.utils.Constants;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {
    private  List<DashboardItem> items;
    private  OnQueryItemSelectedCompletedListener listener;

    public DashboardAdapter(List<DashboardItem> items, Fragment fragment) {
        this.items = items;
        this.listener = (OnQueryItemSelectedCompletedListener) fragment;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final DashboardItemRowBinding binding = DashboardItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DashboardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        holder.bind(items.get(position));


    }

    @Override
    public int getItemCount() {
        if (items.size()>0){
            return items.size();
        }
        return 0;
    }

    public  class DashboardViewHolder extends RecyclerView.ViewHolder{
        private DashboardItemRowBinding binding;
        public DashboardViewHolder(DashboardItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.dashItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelectedListener(items.get(getAdapterPosition()).getItemName());
                }
            });
        }



        public void bind(DashboardItem dashboardItem) {
            binding.setItem(dashboardItem);

        }
    }

}

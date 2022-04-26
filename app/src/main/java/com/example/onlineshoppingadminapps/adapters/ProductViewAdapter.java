package com.example.onlineshoppingadminapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingadminapps.callbacks.OnProductItemClickListener;
import com.example.onlineshoppingadminapps.databinding.ProductItemRowBinding;
import com.example.onlineshoppingadminapps.models.ProductModel;

public class ProductViewAdapter extends ListAdapter<ProductModel, ProductViewAdapter.ProductViewHolder> {
    private OnProductItemClickListener listener;


    public ProductViewAdapter(Fragment fragment) {
        super(new ProductDiff());
        this.listener = (OnProductItemClickListener) fragment;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ProductItemRowBinding binding = ProductItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int p = position;
        holder.bind(getItem(p));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProductItemClicked(getItem(p).getProductId());
            }
        });

    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ProductItemRowBinding binding;
        public ProductViewHolder(ProductItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductModel item) {
            binding.setProduct(item);
        }
    }

    static class ProductDiff extends DiffUtil.ItemCallback<ProductModel>{

        @Override
        public boolean areItemsTheSame(@NonNull ProductModel oldItem, @NonNull ProductModel newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductModel oldItem, @NonNull ProductModel newItem) {
            return oldItem.getProductId().equals(newItem.getProductId());
        }
    }
}

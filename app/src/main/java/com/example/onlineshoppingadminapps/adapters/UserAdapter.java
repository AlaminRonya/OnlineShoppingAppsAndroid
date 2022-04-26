package com.example.onlineshoppingadminapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingadminapps.callbacks.OnUserItemClickListener;
import com.example.onlineshoppingadminapps.databinding.UserItemRowBinding;
import com.example.onlineshoppingadminapps.models.Users;

public class UserAdapter  extends ListAdapter<Users, UserAdapter.UserViewHolder> {

    private  OnUserItemClickListener listener;

    public UserAdapter(Fragment fragment) {
        super(new UserDiff());
        listener = (OnUserItemClickListener) fragment;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final UserItemRowBinding binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        int p = position;
        holder.bind(getItem(p));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserItemClicked(getItem(p).getUserId());
            }
        });

    }


     static class UserViewHolder extends RecyclerView.ViewHolder {
        private final UserItemRowBinding binding;

        public UserViewHolder(UserItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Users item) {
            binding.setUser(item);
        }
    }
    static class UserDiff extends DiffUtil.ItemCallback<Users>{

        @Override
        public boolean areItemsTheSame(@NonNull Users oldItem, @NonNull Users newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Users oldItem, @NonNull Users newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }
    }
}

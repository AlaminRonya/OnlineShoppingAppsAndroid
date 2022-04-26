package com.example.onlineshoppingadminapps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlineshoppingadminapps.adapters.UserAdapter;
import com.example.onlineshoppingadminapps.databinding.FragmentUserListBinding;
import com.example.onlineshoppingadminapps.models.Users;
import com.example.onlineshoppingadminapps.viewmodels.ProductViewModel;

import java.util.List;


public class UserListFragment extends Fragment {
    private FragmentUserListBinding binding;
    private ProductViewModel productViewModel;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);

//        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
//
//        final UserAdapter adapter = new UserAdapter(this);
//        binding.viewUserRV.setLayoutManager(new LinearLayoutManager(getActivity()));
//        binding.viewUserRV.setAdapter(adapter);
//        productViewModel.getAllUsers();
//
//        productViewModel.getUsersListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Users>>() {
//            @Override
//            public void onChanged(List<Users> users) {
//                Toast.makeText(getActivity(), "Value : "+users.size(), Toast.LENGTH_SHORT).show();
//                adapter.submitList(users);
//            }
//        });


        return binding.getRoot();
    }
}
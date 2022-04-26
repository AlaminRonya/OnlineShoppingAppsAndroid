package com.example.onlineshoppingadminapps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onlineshoppingadminapps.adapters.DashboardAdapter;
import com.example.onlineshoppingadminapps.callbacks.OnCheckAdminUserListener;
import com.example.onlineshoppingadminapps.callbacks.OnQueryItemSelectedCompletedListener;
import com.example.onlineshoppingadminapps.databinding.FragmentDashboardBinding;
import com.example.onlineshoppingadminapps.models.DashboardItem;
import com.example.onlineshoppingadminapps.utils.Constants;
import com.example.onlineshoppingadminapps.viewmodels.LoginViewModel;

public class DashboardFragment extends Fragment implements OnQueryItemSelectedCompletedListener, OnCheckAdminUserListener {
    private FragmentDashboardBinding binding;
    private LoginViewModel loginViewModel;
    private boolean isCheck;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        loginViewModel.getStateLiveData().observe(getViewLifecycleOwner(), authState -> {
            if (authState == LoginViewModel.AuthState.UNAUTHENTICATED) {
                Navigation.findNavController(container).navigate(R.id.action_dashboardFragment_to_loginFragment);
            }
        });

        final DashboardAdapter adapter;
        if (isCheck){
            adapter = new DashboardAdapter(DashboardItem.getDashboardItems(), this);
        }else {
            adapter = new DashboardAdapter(DashboardItem.getDashboardItemsUsers(), this);
        }
        final GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        binding.dashboardRV.setLayoutManager(glm);
        binding.dashboardRV.setAdapter(adapter);



        return binding.getRoot();

    }

    @Override
    public void onItemSelectedListener(String item) {
        switch (item) {
            case Constants.Item.ADD_PRODUCT:
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_dashboardFragment_to_addProductFragment);
                break;
            case Constants.Item.VIEW_PRODUCT:
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_dashboardFragment_to_viewProductFragment);
                break;

            case Constants.Item.VIEW_USERS:
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_dashboardFragment_to_userListFragment);
                break;
        }
    }



    @Override
    public void onAdminOrUserSelect(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
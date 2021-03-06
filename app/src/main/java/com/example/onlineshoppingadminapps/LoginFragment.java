package com.example.onlineshoppingadminapps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshoppingadminapps.databinding.FragmentLoginBinding;
import com.example.onlineshoppingadminapps.utils.HelperFunctions;
import com.example.onlineshoppingadminapps.viewmodels.LoginViewModel;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        loginViewModel.getStateLiveData()
                .observe(getViewLifecycleOwner(), authState -> {
                    if (authState == LoginViewModel.AuthState.AUTHENTICATED) {
                        Navigation.findNavController(container)
                                .navigate(R.id.action_loginFragment_to_dashboardFragment);
                    }
                });

        loginViewModel.getErrMsgLiveData()
                .observe(getViewLifecycleOwner(), errMsg -> {
                    binding.errMsgTV.setText(errMsg);
                });

        binding.loginBtn.setOnClickListener(v -> {
            final String email = binding.emailInputET.getText().toString();
            final String password = binding.passwordInputET.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                HelperFunctions.showToast(getActivity(), "Please provide both field values");
                return;
            }

            loginViewModel.login(email, password);
        });
        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(container).navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });

        return binding.getRoot();
    }
}
package com.example.onlineshoppingadminapps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshoppingadminapps.databinding.FragmentRegistrationBinding;
import com.example.onlineshoppingadminapps.models.Users;
import com.example.onlineshoppingadminapps.viewmodels.LoginViewModel;


public class RegistrationFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    private LoginViewModel loginViewModel;



    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(container).navigate(R.id.action_registrationFragment_to_loginFragment);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
                Navigation.findNavController(container).navigate(R.id.action_registrationFragment_to_loginFragment);
            }
        });


        return binding.getRoot();
    }

    private void saveProduct() {
        final String firstName = binding.etFirstName.getText().toString();
        final String phoneNumber = binding.etPhoneNumber.getText().toString();
        final String email = binding.etEmail.getText().toString();
        final String password = binding.etPassword.getText().toString();
        final String confirmPassword = binding.etConfirmPassword.getText().toString();
        if (firstName.isEmpty()){
            binding.etFirstName.setText("Invalid");
            binding.etFirstName.setFocusable(true);
            return;
        }
        if (phoneNumber.isEmpty()){
            binding.etFirstName.setText("Invalid");
            binding.etPhoneNumber.setFocusable(true);
            return;
        }
        if (email.isEmpty()){
            binding.etEmail.setText("Invalid");
            binding.etEmail.setFocusable(true);
            return;
        }

        if (password.isEmpty()){
            binding.etPassword.setText("Invalid");
            binding.etPassword.setFocusable(true);
            return;
        }
        if (confirmPassword.isEmpty()){
            binding.etConfirmPassword.setText("Invalid");
            binding.etConfirmPassword.setFocusable(true);
            return;
        }

        if (password.equals(confirmPassword)) {
            final Users users = new Users(null, firstName, phoneNumber, email, password);
            authenticate(users);
        }


    }
    private void authenticate(Users users) {
        loginViewModel.register(users);

    }

}
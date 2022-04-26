package com.example.onlineshoppingadminapps.viewmodels;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshoppingadminapps.DashboardFragment;
import com.example.onlineshoppingadminapps.callbacks.OnCheckAdminListener;
import com.example.onlineshoppingadminapps.callbacks.OnCheckAdminUserListener;
import com.example.onlineshoppingadminapps.models.Users;
import com.example.onlineshoppingadminapps.repositories.AdminRepository;
import com.example.onlineshoppingadminapps.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {

    public enum AuthState{
        AUTHENTICATED,UNAUTHENTICATED
    }

    private MutableLiveData<AuthState> stateLiveData;
    private MutableLiveData<String> errMsgLiveData;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private OnCheckAdminUserListener listener;
    private final AdminRepository adminRepository = new AdminRepository();

    public LoginViewModel() {
        this.listener = new DashboardFragment();
        stateLiveData = new MutableLiveData<>();
        errMsgLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null){
            stateLiveData.postValue(AuthState.AUTHENTICATED);
        }else {
            stateLiveData.postValue(AuthState.UNAUTHENTICATED);
        }

    }

    public LiveData<AuthState> getStateLiveData() {
        return stateLiveData;
    }

    public LiveData<String> getErrMsgLiveData() {
        return errMsgLiveData;
    }

    public FirebaseUser getUser() {
        return user;
    }
    public void logout(){
        if (user != null){
            auth.signOut();
            stateLiveData.postValue(AuthState.UNAUTHENTICATED);
        }
    }

    public void login(String email, String password){

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        adminRepository.checkAdmin(auth.getUid(), new OnCheckAdminListener() {
                            @Override
                            public void onCheckAdmin(boolean status) {
                                if (status){
//                                    isAdmin = true;
                                    listener.onAdminOrUserSelect(true);

                                }else {
                                    listener.onAdminOrUserSelect(false);
                                }


                                user = authResult.getUser();
                                stateLiveData.postValue(AuthState.AUTHENTICATED);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errMsgLiveData.postValue(e.getLocalizedMessage());
            }
        });
    }



    public void register(Users users) {
        auth.createUserWithEmailAndPassword(users.getEmail(), users.getPassword())
                .addOnSuccessListener(authResult -> {
                    user = authResult.getUser();
//                    stateLiveData.postValue(AuthState.AUTHENTICATED);
                    addUserToDatabase(users);
                }).addOnFailureListener(e -> {
            errMsgLiveData.postValue(e.getLocalizedMessage());
        });
    }
    private void addUserToDatabase(Users users) {
        users.setUserId(user.getUid());
//        final Users ecomUser = new Users(user.getUid(), null, null, user.getEmail(), null);

        adminRepository.addUser(users);
    }
}

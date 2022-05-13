package com.example.foodex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodex.data.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<String> getAuthenticationMessage() {
        return userRepository.getAuthenticationMessage();
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return userRepository.getProgressBar();
    }

    public MutableLiveData<Boolean> getCompleted() {
        return userRepository.getCompleted();
    }

    public void register(String email, String password, String fullName)
    {
        userRepository.register(email, password, fullName);
    }
}

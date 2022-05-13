package com.example.foodex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foodex.data.UserRepository;

public class HomeViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }
}

package com.example.foodex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodex.data.UserRepository;
import com.example.foodex.data.models.Result;

import java.util.ArrayList;

public class PantryViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public PantryViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<ArrayList<Result>> getPantryList()
    {
        return userRepository.getPantryList();
    }

    public void getPantry()
    {
        userRepository.getPantry();
    }
}

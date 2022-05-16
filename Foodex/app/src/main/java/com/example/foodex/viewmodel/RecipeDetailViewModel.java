package com.example.foodex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foodex.data.UserRepository;

public class RecipeDetailViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<String> getFullName() {
        return userRepository.getFullName();
    }

    public MutableLiveData<String> getEmail()
    {
        return userRepository.getEmail();
    }

    public void addFavorite(String id)
    {
        userRepository.addFavorite(id);
    }

}

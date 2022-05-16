package com.example.foodex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.foodex.data.UserRepository;

import java.util.ArrayList;

public class FavoritesViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public FavoritesViewModel(@NonNull Application application) {
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

    public MutableLiveData<ArrayList<String>> getFavoritesList()
    {
        return userRepository.getFavoritesList();
    }

    public void getFavorites()
    {
        userRepository.getFavorites();
    }

    public String reorderList(ArrayList<String> id)
    {
        if (id.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < id.size(); i++) {
            sb.append(id.get(i));
            if(i == id.size()-1) {
                return sb.toString();
            }
            sb.append(',');
        }
        return sb.toString();
    }
}

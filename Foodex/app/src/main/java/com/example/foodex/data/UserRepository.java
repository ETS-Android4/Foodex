package com.example.foodex.data;

import androidx.lifecycle.MutableLiveData;

import com.example.foodex.data.models.Result;

import java.util.ArrayList;

public class UserRepository {
    private static UserRepository instance;
    private UserDAO userDAO;

    public UserRepository() {
        userDAO = UserDAO.getInstance();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void register(String email, String password, String fullName) {
        userDAO.register(email, password, fullName);
    }

    public MutableLiveData<String> getEmail()
    {
        return userDAO.getEmail();
    }

    public MutableLiveData<String> getFullName()
    {
        return userDAO.getFullName();
    }

    public void login(String email, String password)
    {
        userDAO.login(email, password);
    }

    public void signOut()
    {
        userDAO.signOut();
    }

    public void resetPassword(String email)
    {
        userDAO.resetPassword(email);
    }

    public MutableLiveData<String> getAuthenticationMessage() {
        return userDAO.getAuthenticationMessage();
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return userDAO.getProgressBar();
    }

    public MutableLiveData<Boolean> getCompleted() {
        return userDAO.getCompleted();
    }

    public void getProfile()
    {
        userDAO.getProfile();
    }

    public void addFavorite(String id)
    {
        userDAO.addFavorite(id);
    }

    public MutableLiveData<ArrayList<String>> getFavoritesList()
    {
        return userDAO.getFavoritesList();
    }

    public void getFavorites()
    {
        userDAO.getFavorites();
    }

    public void addIngredient(Result ingredient)
    {
        userDAO.addIngredient(ingredient);
    }
    public MutableLiveData<ArrayList<Result>> getPantryList()
    {
        return userDAO.getPantryList();
    }

    public void getPantry()
    {
        userDAO.getPantry();
    }


}

package com.example.foodex.data;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String fullName, email;
    public List<String> favorites;

    public User(){

    }

    public User(String fullName, String email)
    {
        this.fullName = fullName;
        this.email = email;
        this.favorites = new ArrayList<>();
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void addFavorite(String id)
    {
        favorites.add(id);
    }
}

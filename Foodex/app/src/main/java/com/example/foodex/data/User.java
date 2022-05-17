package com.example.foodex.data;

import java.util.HashMap;

public class User {

    public String fullName, email;
    public HashMap<String, String> list;

    public User(){

    }

    public User(String fullName, String email)
    {
        this.fullName = fullName;
        this.email = email;
        this.list = new HashMap<>();
    }

    public User(String fullName, String email, HashMap<String, String> favorites)
    {
        this.fullName = fullName;
        this.email = email;
        this.list = favorites;
    }


}

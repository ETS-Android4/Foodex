package com.example.foodex.data.models;

public class Result {
    public int id;
    public String name;
    public String image;

    public Result()
    {

    }

    public Result(String name, String image)
    {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}

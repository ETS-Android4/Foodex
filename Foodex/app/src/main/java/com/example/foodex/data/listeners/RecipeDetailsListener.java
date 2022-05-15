package com.example.foodex.data.listeners;

import com.example.foodex.data.models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}

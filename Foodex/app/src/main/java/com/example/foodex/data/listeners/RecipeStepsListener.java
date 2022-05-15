package com.example.foodex.data.listeners;

import com.example.foodex.data.models.RecipeStepsResponse;

import java.util.List;

public interface RecipeStepsListener {
    void didFetch(List<RecipeStepsResponse> response, String message);
    void didError(String message);
}

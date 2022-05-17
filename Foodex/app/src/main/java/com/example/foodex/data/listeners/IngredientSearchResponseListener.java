package com.example.foodex.data.listeners;

import com.example.foodex.data.models.IngredientSearchResponse;

public interface IngredientSearchResponseListener {
    void didFetch(IngredientSearchResponse response, String message);
    void didError(String message);
}

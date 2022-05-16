package com.example.foodex.data.listeners;

import com.example.foodex.data.models.FavoriteRecipesApiResponse;

public interface FavoriteRecipesResponseListener {
    void didFetch(FavoriteRecipesApiResponse response, String message);
    void didError(String message);
}

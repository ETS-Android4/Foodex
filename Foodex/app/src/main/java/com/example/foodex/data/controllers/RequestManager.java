package com.example.foodex.data.controllers;

import android.content.Context;
import android.util.Log;

import com.example.foodex.R;
import com.example.foodex.data.listeners.FavoriteRecipesResponseListener;
import com.example.foodex.data.listeners.IngredientSearchResponseListener;
import com.example.foodex.data.listeners.RandomRecipeResponseListener;
import com.example.foodex.data.listeners.RecipeDetailsListener;
import com.example.foodex.data.listeners.RecipeStepsListener;
import com.example.foodex.data.models.FavoriteRecipesApiResponse;
import com.example.foodex.data.models.IngredientSearchResponse;
import com.example.foodex.data.models.RecipeStepsResponse;
import com.example.foodex.data.models.RandomRecipeApiResponse;
import com.example.foodex.data.models.RecipeDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Log.e("Recipe", "Starting the call for api!!!!");
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe("10", context.getString(R.string.api_key), tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    Log.e("Recipe", "Not Successful!");
                    return;
                }
                listener.didFetch(response.body(), response.message());
                Log.e("Recipe", "Successful!");
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
                Log.e("Recipe", "FAILURE RECIPE RANDOM");
            }
        });
    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id)
    {
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful())
                {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeSteps(RecipeStepsListener listener, int id)
    {
        CallRecipeSteps callRecipeSteps = retrofit.create(CallRecipeSteps.class);
        Call<List<RecipeStepsResponse>> call = callRecipeSteps.callRecipeSteps(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<RecipeStepsResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeStepsResponse>> call, Response<List<RecipeStepsResponse>> response) {
                if(!response.isSuccessful())
                {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<RecipeStepsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getFavoriteRecipes(FavoriteRecipesResponseListener listener, String ids)
    {
        CallFavoriteRecipes callFavoriteRecipes = retrofit.create(CallFavoriteRecipes.class);
        Log.e("FavoriteRecipe", "Starting the call for api!!!!");
        Call<FavoriteRecipesApiResponse> call = callFavoriteRecipes.callFavoriteRecipes(ids, context.getString(R.string.api_key));
        call.enqueue(new Callback<FavoriteRecipesApiResponse>() {
            @Override
            public void onResponse(Call<FavoriteRecipesApiResponse> call, Response<FavoriteRecipesApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    Log.e("Recipe", "Not Successful!");
                    return;
                }
                listener.didFetch(response.body(), response.message());
                Log.e("Recipe", "Successful!");
            }

            @Override
            public void onFailure(Call<FavoriteRecipesApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
                Log.e("Recipe", "FAILURE FAVORITE RECIPE");
            }
        });
    }

    public void getIngredientSearch(IngredientSearchResponseListener listener, String name)
    {
        CallIngredientSearch callIngredientSearch = retrofit.create(CallIngredientSearch.class);
        Log.e("Pantry", "Starting the call for api!!!!");
        Call<IngredientSearchResponse> call = callIngredientSearch.callIngredientSearch(name, context.getString(R.string.api_key));
        call.enqueue(new Callback<IngredientSearchResponse>() {
            @Override
            public void onResponse(Call<IngredientSearchResponse> call, Response<IngredientSearchResponse> response) {
                if(!response.isSuccessful())
                {
                    listener.didError(response.message());
                    Log.e("Pantry", "Not Successful!");
                    return;
                }
                listener.didFetch(response.body(), response.message());
                Log.e("Pantry", "Successful!");
            }

            @Override
            public void onFailure(Call<IngredientSearchResponse> call, Throwable t) {
                listener.didError(t.getMessage());
                Log.e("Pantry", "FAILURE SEARCH INGREDIENTS");
            }
        });
    }


    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("number") String number,
                @Query("apiKey") String apiKey,
                @Query("tags") List<String> tags);

    }

    private interface CallRecipeDetails {
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallRecipeSteps{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<RecipeStepsResponse>> callRecipeSteps(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallFavoriteRecipes{
        @GET("recipes/informationBulk")
        Call<FavoriteRecipesApiResponse> callFavoriteRecipes(
                @Query(value = "ids", encoded = true) String ids,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallIngredientSearch{
        @GET("food/ingredients/search")
        Call<IngredientSearchResponse> callIngredientSearch(
                @Query("query") String name,
                @Query("apiKey") String apiKey
        );
    }
}

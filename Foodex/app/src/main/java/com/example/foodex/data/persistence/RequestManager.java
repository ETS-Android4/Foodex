package com.example.foodex.data.persistence;

import android.content.Context;
import android.util.Log;

import com.example.foodex.R;
import com.example.foodex.data.listeners.RandomRecipeResponseListener;
import com.example.foodex.data.models.RandomRecipeApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
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

    public void getRandomRecipes(RandomRecipeResponseListener listener)
    {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Log.e("Recipe", "Starting the call for api!!!!");
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe("10", context.getString(R.string.api_key));
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful())
                {
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

    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("number") String number,
                @Query("apiKey") String apiKey);

    }
}

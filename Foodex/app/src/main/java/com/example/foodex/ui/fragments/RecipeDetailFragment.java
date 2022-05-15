package com.example.foodex.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.adapters.IngredientsAdapter;
import com.example.foodex.data.listeners.RecipeDetailsListener;
import com.example.foodex.data.models.RecipeDetailsResponse;
import com.example.foodex.data.controllers.RequestManager;
import com.squareup.picasso.Picasso;

public class RecipeDetailFragment extends Fragment {

    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_food;
    RecyclerView recycler_ingredients;
    View view;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        id = Integer.parseInt(getArguments().getString("id"));

        findViews();

        manager = new RequestManager(getActivity());
        manager.getRecipeDetails(listener, id);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");
        dialog.show();

        return view;
    }

    private void findViews()
    {
        textView_meal_name = view.findViewById(R.id.textView_meal_name);
        textView_meal_source = view.findViewById(R.id.textView_meal_source);
        textView_meal_summary = view.findViewById(R.id.textView_meal_summary);
        imageView_food = view.findViewById(R.id.imageView_food);
        recycler_ingredients = view.findViewById(R.id.recycler_ingredients);
    }

    private final RecipeDetailsListener listener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.creditsText);
            textView_meal_summary.setText(Html.fromHtml(response.summary));
            Picasso.get().load(response.image).into(imageView_food);

            recycler_ingredients.setHasFixedSize(true);
            recycler_ingredients.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            adapter = new IngredientsAdapter(getActivity(), response.extendedIngredients);
            recycler_ingredients.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    };
}
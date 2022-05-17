package com.example.foodex.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.adapters.IngredientsAdapter;
import com.example.foodex.data.adapters.InstructionsAdapter;
import com.example.foodex.data.listeners.RecipeDetailsListener;
import com.example.foodex.data.listeners.RecipeStepsListener;
import com.example.foodex.data.models.RecipeDetailsResponse;
import com.example.foodex.data.controllers.RequestManager;
import com.example.foodex.data.models.RecipeStepsResponse;
import com.example.foodex.viewmodel.RecipeDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailFragment extends Fragment {

    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_food;
    RecyclerView recycler_ingredients, recycler_instructions;
    Button button_add_favorite;
    View view;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter adapter;
    InstructionsAdapter instructionsAdapter;

    RecipeDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        id = Integer.parseInt(getArguments().getString("id"));

        viewModel = new ViewModelProvider(this).get(RecipeDetailViewModel.class);
        findViews();

        button_add_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addFavorite(String.valueOf(id));
                Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
            }
        });

        manager = new RequestManager(getActivity());
        manager.getRecipeDetails(listener, id);
        manager.getRecipeSteps(recipeStepsListener, id);

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
        recycler_instructions = view.findViewById(R.id.recycler_instructions);
        button_add_favorite = view.findViewById(R.id.button_add_favorite);
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

    private final RecipeStepsListener recipeStepsListener = new RecipeStepsListener() {
        @Override
        public void didFetch(List<RecipeStepsResponse> response, String message) {
            recycler_instructions.setHasFixedSize(true);
            recycler_instructions.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(getActivity(), response);
            recycler_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };
}
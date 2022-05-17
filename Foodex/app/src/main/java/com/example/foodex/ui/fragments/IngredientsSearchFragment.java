package com.example.foodex.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.adapters.SearchIngredientsAdapter;
import com.example.foodex.data.controllers.RequestManager;
import com.example.foodex.data.listeners.IngredientClickListener;
import com.example.foodex.data.listeners.IngredientSearchResponseListener;
import com.example.foodex.data.models.IngredientSearchResponse;
import com.example.foodex.data.models.Result;
import com.example.foodex.viewmodel.IngredientSearchViewModel;

public class IngredientsSearchFragment extends Fragment {

    TextView textView_search_title;
    RecyclerView recycler_search_ingredients;
    String search;
    RequestManager manager;
    Dialog dialog;
    SearchIngredientsAdapter adapter;
    View view;
    IngredientSearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_ingredients_search, container, false);

        search = getArguments().getString("search");

        viewModel = new ViewModelProvider(this).get(IngredientSearchViewModel.class);

        textView_search_title = view.findViewById(R.id.textView_search_title);
        recycler_search_ingredients = view.findViewById(R.id.recycler_search_ingredients);

        manager = new RequestManager(getActivity());
        manager.getIngredientSearch(listener, search);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");
        dialog.show();

        textView_search_title.setText("Search Results For: " + search);

        return view;
    }

    private final IngredientSearchResponseListener listener = new IngredientSearchResponseListener() {
        @Override
        public void didFetch(IngredientSearchResponse response, String message) {
            dialog.dismiss();

            recycler_search_ingredients.setHasFixedSize(true);
            recycler_search_ingredients.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            adapter = new SearchIngredientsAdapter(getActivity(), response.results, ingredientClickListener);
            recycler_search_ingredients.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final IngredientClickListener ingredientClickListener = new IngredientClickListener() {
        @Override
        public void onIngredientClick(Result ingredient) {
            viewModel.addIngredient(ingredient);
            Toast.makeText(getActivity(), ingredient.name + " has been added to your pantry!", Toast.LENGTH_SHORT).show();
        }
    };
}
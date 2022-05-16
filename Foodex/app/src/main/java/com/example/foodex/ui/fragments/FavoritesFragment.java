package com.example.foodex.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.adapters.FavoriteRecipeAdapter;
import com.example.foodex.data.controllers.RequestManager;
import com.example.foodex.data.listeners.FavoriteRecipesResponseListener;
import com.example.foodex.data.listeners.RecipeClickListener;
import com.example.foodex.data.models.FavoriteRecipesApiResponse;
import com.example.foodex.viewmodel.FavoritesViewModel;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {


    FavoritesViewModel viewModel;
    RequestManager manager;
    Dialog dialog;
    View view;
    RecyclerView recyclerView;
    FavoriteRecipeAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);


        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");

        manager = new RequestManager(getActivity());
        dialog.show();
        viewModel.getFavoritesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                manager.getFavoriteRecipes(favoriteRecipesResponseListener, viewModel.reorderList(strings));
            }
        });

        viewModel.getFavorites();

        return view;
    }

    private final FavoriteRecipesResponseListener favoriteRecipesResponseListener = new FavoriteRecipesResponseListener() {
        @Override
        public void didFetch(FavoriteRecipesApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = view.findViewById(R.id.recycler_favorites);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            adapter = new FavoriteRecipeAdapter(getActivity(), response.list, recipeClickListener);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            RecipeDetailFragment nextFrag = new RecipeDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            nextFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    };
}

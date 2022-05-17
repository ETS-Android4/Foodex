package com.example.foodex.ui.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.adapters.PantryAdapter;
import com.example.foodex.data.adapters.SearchIngredientsAdapter;
import com.example.foodex.data.models.Ingredient;
import com.example.foodex.data.models.Result;
import com.example.foodex.viewmodel.IngredientSearchViewModel;
import com.example.foodex.viewmodel.PantryViewModel;

import java.util.ArrayList;

public class PantryFragment extends Fragment {

    SearchView searchView_pantry;
    View view;
    PantryViewModel viewModel;
    RecyclerView recycler_pantry;
    Dialog dialog;
    PantryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_pantry, container, false);

        viewModel = new ViewModelProvider(this).get(PantryViewModel.class);

        recycler_pantry = view.findViewById(R.id.recycler_pantry);
        searchView_pantry = view.findViewById(R.id.searchView_pantry);

        searchView_pantry.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                IngredientsSearchFragment nextFrag= new IngredientsSearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("search", query);
                nextFrag.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewModel.getPantryList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Result>>() {
            @Override
            public void onChanged(ArrayList<Result> pantryList) {
                dialog.dismiss();
                recycler_pantry.setHasFixedSize(true);
                recycler_pantry.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                adapter = new PantryAdapter(getActivity(), pantryList);
                recycler_pantry.setAdapter(adapter);
            }
        });

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");
        dialog.show();



        viewModel.getPantry();

        return view;
    }
}
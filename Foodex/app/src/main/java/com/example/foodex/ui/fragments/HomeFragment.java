package com.example.foodex.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.adapters.RandomRecipeAdapter;
import com.example.foodex.data.listeners.RandomRecipeResponseListener;
import com.example.foodex.data.models.RandomRecipeApiResponse;
import com.example.foodex.data.persistence.RequestManager;

public class HomeFragment extends Fragment {

    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;

    Spinner spinner;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Loading dialog
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");

        //Spinner
        spinner = view.findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.tags, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);

        //API manager
        manager = new RequestManager(getActivity());
        manager.getRandomRecipes(randomRecipeResponseListener);

        dialog.show();
        return view;
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = view.findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            randomRecipeAdapter = new RandomRecipeAdapter(getActivity(), response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //TODO spinner
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //TODO spinner
        }
    };
}
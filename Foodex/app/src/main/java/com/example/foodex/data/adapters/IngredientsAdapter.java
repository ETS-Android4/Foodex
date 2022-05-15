package com.example.foodex.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.models.ExtendedIngredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder>{

    Context context;
    List<ExtendedIngredient> list;

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.textView_amount.setText(list.get(position).measures.metric.amount + list.get(position).measures.metric.unitShort);
        holder.textView_ingredient_name.setText(list.get(position).name);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {

    TextView textView_amount, textView_ingredient_name;

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_amount = itemView.findViewById(R.id.textView_amount);
        textView_ingredient_name = itemView.findViewById(R.id.textView_ingredient_name);
    }
}

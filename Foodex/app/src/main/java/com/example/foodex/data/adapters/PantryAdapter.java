package com.example.foodex.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.listeners.IngredientClickListener;
import com.example.foodex.data.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryViewHolder>{

    Context context;
    List<Result> list;

    public PantryAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PantryViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        holder.textView_step_item.setText(list.get(position).name);
        holder.textView_step_item.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + list.get(position).image).into(holder.imageView_step_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class PantryViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_step_item;
    TextView textView_step_item;
    CardView cardView_items;

    public PantryViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView_step_item = itemView.findViewById(R.id.imageView_step_item);
        textView_step_item = itemView.findViewById(R.id.textView_step_item);
        cardView_items = itemView.findViewById(R.id.cardView_items);
    }
}
package com.example.foodex.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodex.R;
import com.example.foodex.data.models.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    Context context;
    List<Step> list;

    public StepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.textView_step_number.setText(String.valueOf(list.get(position).number));
        holder.textView_step_title.setText(list.get(position).step);

        //Ingredients
        if (list.get(position).ingredients.isEmpty()) {
            holder.textView_instruction_step_ingredients.setText("");
        }
        holder.recycler_step_ingredients.setHasFixedSize(true);
        holder.recycler_step_ingredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionIngredientsAdapter instructionIngredientsAdapter = new InstructionIngredientsAdapter(context, list.get(position).ingredients);
        holder.recycler_step_ingredients.setAdapter(instructionIngredientsAdapter);

        //Equipment
        if (list.get(position).equipment.isEmpty()) {
            holder.textView_instruction_step_equipment.setText("");
        }
        holder.recycler_step_equipment.setHasFixedSize(true);
        holder.recycler_step_equipment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionEquipmentAdapter instructionEquipmentAdapter = new InstructionEquipmentAdapter(context, list.get(position).equipment);
        holder.recycler_step_equipment.setAdapter(instructionEquipmentAdapter);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class StepViewHolder extends RecyclerView.ViewHolder {

    TextView textView_step_number, textView_step_title, textView_instruction_step_equipment, textView_instruction_step_ingredients;
    RecyclerView recycler_step_equipment, recycler_step_ingredients;

    public StepViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_step_number = itemView.findViewById(R.id.textView_step_number);
        textView_step_title = itemView.findViewById(R.id.textView_step_title);
        textView_instruction_step_equipment = itemView.findViewById(R.id.textView_instruction_step_equipment);
        textView_instruction_step_ingredients = itemView.findViewById(R.id.textView_instruction_step_ingredients);
        recycler_step_equipment = itemView.findViewById(R.id.recycler_step_equipment);
        recycler_step_ingredients = itemView.findViewById(R.id.recycler_step_ingredients);
    }
}

package com.example.exercise_13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exercise_13.databinding.ItemDropdownBinding;

import java.util.ArrayList;

public class MealAdapter extends ArrayAdapter<Meal> {
    ArrayList<Meal> meals = new ArrayList<>();
    Context context;

    public MealAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Nullable
    @Override
    public Meal getItem(int position) {
        return meals.get(position);
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            ItemDropdownBinding binding = ItemDropdownBinding.inflate(LayoutInflater.from(context),parent,false);
            binding.getRoot().setText(meals.get(position).getName());
            return binding.getRoot();
        }
        else{
            return super.getView(position,convertView,parent);
        }

    }
}

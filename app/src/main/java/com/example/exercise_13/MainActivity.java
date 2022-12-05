package com.example.exercise_13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.exercise_13.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    private int mealQuantity = 0;
    private double tax = 0.13;
    private ArrayList<Meal> meals = new ArrayList<>();
//    ArrayList<String> mealNames = new ArrayList<>();
    private Meal selectedMeal ;

    private ActivityMainBinding binding;
    MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addMeals();

//        ArrayAdapter ad
//                = new ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item,
//                mealNames);
//        ad.setDropDownViewResource(
//                android.R.layout
//                        .simple_spinner_dropdown_item);
//        binding.mealSpinner.setAdapter(ad);

        adapter = new MealAdapter(this, android.R.layout.simple_dropdown_item_1line);
        adapter.meals = meals;
        binding.mealSpinner.setAdapter(adapter);

        binding.mealSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.tenRadioButton).setOnClickListener(this);
        findViewById(R.id.fifteenRadioButton).setOnClickListener(this);
        findViewById(R.id.twentyRadioButton).setOnClickListener(this);

        binding.quantitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mealQuantity = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                totalPrice();
            }
        });

        binding.order.setOnClickListener(
                v -> {
                    if(selectedMeal == null){
                        Toast.makeText(this, "Please select the meal.", Toast.LENGTH_SHORT).show();
                    }else {
                        if (binding.confirmCheckBox.isChecked()) {
                            if (mealQuantity != 0) {
                                Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(this, "Please select the meal quantity.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "You need to conform the order", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        binding.confirmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.order.setEnabled(b);
            }
        });
    }

    private void totalPrice(){
        if(selectedMeal != null) {
            double totalPriceAmount = mealQuantity * selectedMeal.getPrice();
            double taxAmount = totalPriceAmount * tax;
            int dealId = binding.tipButtonGroup.getCheckedRadioButtonId();
            String value = ((RadioButton) findViewById(dealId)).getText().toString();
            double tip = Double.parseDouble(value.replace("%","")) / 100.0;
            double tipAmount = totalPriceAmount * tip;
            double finalTotal = totalPriceAmount + taxAmount + tipAmount;
            String finalTotalString = String.format("%.2f", finalTotal);
            binding.totalPrice.setText(finalTotalString);
        }else{
            binding.priceET.setText("0");
            binding.totalPrice.setText("0");
        }
    }

    private void addMeals(){
        meals.add(new Meal("Fried Chicken",15.5,R.drawable.checken));
        meals.add(new Meal("Noodles",10.,R.drawable.noodles));
        meals.add(new Meal("Beef",25.5,R.drawable.beef));
        meals.add(new Meal("Fried Rice",5.8, R.drawable.rice));
        meals.add(new Meal("Fries",5.8,R.drawable.frenchfies));
        meals.add(new Meal("Prawns",5.8,R.drawable.prawns));
//        for(Meal meal:meals){
//            mealNames.add(meal.getName());
//        }
    }
//
    @Override
    public void onClick(View v) {
        totalPrice();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        selectedMeal = meals.get(position);
        binding.priceET.setText(String.valueOf(selectedMeal.getPrice()));
        totalPrice();
        binding.mealImage.setImageResource(selectedMeal.getImage());
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
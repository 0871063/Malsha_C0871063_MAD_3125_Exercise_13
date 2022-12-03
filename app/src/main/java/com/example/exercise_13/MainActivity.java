package com.example.exercise_13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    private EditText price;
    private EditText totalPrice;
    private Spinner mealSpinner;
    private SeekBar quantity;
    private RadioGroup tips;
    private ImageView mealImage;
    private CheckBox confirm;
    private Button order;
    private int mealQuantity = 0;
    private double tax = 0.13;
    private ArrayList<Meal> meals = new ArrayList<>();
    ArrayList<String> mealNames = new ArrayList<>();
    private Meal selectedMeal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        price = findViewById(R.id.price);
        totalPrice = findViewById(R.id.totalPrice);
        mealSpinner = findViewById(R.id.mealSpinner);
        quantity = findViewById(R.id.quantitySeekBar);
        tips = findViewById(R.id.tipButtonGroup);
        mealImage = findViewById(R.id.mealImage);
        confirm = findViewById(R.id.confirmCheckBox);
        order = findViewById(R.id.order);
        quantity.setMin(0);
        quantity.setMax(10);

        addMeals();

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                mealNames);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mealSpinner.setAdapter(ad);
        mealSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.tenRadioButton).setOnClickListener(this);
        findViewById(R.id.fifteenRadioButton).setOnClickListener(this);
        findViewById(R.id.twentyRadioButton).setOnClickListener(this);

        quantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        order.setOnClickListener(
                v -> {
                    if(selectedMeal == null){
                        Toast.makeText(this, "Please select the meal.", Toast.LENGTH_SHORT).show();
                    }else {
                        if (confirm.isChecked()) {
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
    }

    private void totalPrice(){
        if(selectedMeal != null) {
            double totalPriceAmount = mealQuantity * selectedMeal.getPrice();
            double taxAmount = totalPriceAmount * tax;
            int dealId = tips.getCheckedRadioButtonId();
            String value = ((RadioButton) findViewById(dealId)).getText().toString();
            double tip = Double.parseDouble(value.replace("%","")) / 100.0;
            double tipAmount = totalPriceAmount * tip;
            double finalTotal = totalPriceAmount + taxAmount + tipAmount;
            String finalTotalString = String.format("%.2f", finalTotal);
            totalPrice.setText(finalTotalString);
        }else{
            price.setText("0");
            totalPrice.setText("0");
        }
    }

    private void addMeals(){
        meals.add(new Meal("Fried Chicken",15.5,R.drawable.checken));
        meals.add(new Meal("Noodles",10.,R.drawable.noodles));
        meals.add(new Meal("Beef",25.5,R.drawable.beef));
        meals.add(new Meal("Fried Rice",5.8, R.drawable.rice));
        meals.add(new Meal("Fries",5.8,R.drawable.frenchfies));
        meals.add(new Meal("Prawns",5.8,R.drawable.prawns));
        for(Meal meal:meals){
            mealNames.add(meal.getName());
        }
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
        price.setText(String.valueOf(selectedMeal.getPrice()));
        totalPrice();
        mealImage.setImageResource(selectedMeal.getImage());
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
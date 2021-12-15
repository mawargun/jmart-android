package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.model.Product;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request.CreateProductRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static  Product product = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        setContentView(R.layout.activity_create_product);
        EditText productName = findViewById(R.id.InputNameCreate);
        EditText productWeight = findViewById(R.id.InputWeightCreate);
        EditText productPrice = findViewById(R.id.InputPriceCreate);
        EditText productDiscount = findViewById(R.id.InputDiscountCreate);
        CheckBox newCheck = findViewById(R.id.NewRadiobutton);
        CheckBox usedCheck = findViewById(R.id.UsedRadiobutton);
        Spinner category = findViewById(R.id.SpinnerCategory);
        Spinner shipmentPlans = findViewById(R.id.SpinnerShipment);
        Button create = findViewById(R.id.ButtonCreate);
        Button cancel = findViewById(R.id.ButtonCancel);;

        newCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    usedCheck.setChecked(false);
                }
            }
        });

        usedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    newCheck.setChecked(false);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productName.setText("");
                productWeight.setText("");
                productPrice.setText("");
                productDiscount.setText("");
                usedCheck.setChecked(false);
                newCheck.setChecked(false);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            product = gson.fromJson(object.toString(), Product.class);
                            Toast.makeText(CreateProductActivity.this,"Product Terdaftar",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        System.out.println(product);
                    }
                };
                CreateProductRequest requestProduct = new CreateProductRequest(productName.getText().toString(),productWeight.getText().toString(),String.valueOf(newCheck.isChecked()),productPrice.getText().toString(),productDiscount.getText().toString(),category.getSelectedItem().toString(),convertShipmentPlans(shipmentPlans.getSelectedItem().toString()),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
                requestQueue.add(requestProduct);
            }
        });
    }

    protected String convertShipmentPlans(String shipment){
        switch (shipment) {
            case "INSTANT":
                return "0";
            case "SAME DAY":
                return "1";
            case "NEXT DAY":
                return "2";
            case "REGULER":
                return "3";
            default:
                return "4";
        }
    }
}
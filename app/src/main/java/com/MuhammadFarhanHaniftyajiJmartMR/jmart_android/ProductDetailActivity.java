package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

/**
 * Payment ProductDetailActivity class here
 * This class displays a page containing user-selected product information
 * from the product fragment in the main activity
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 */

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView name = (TextView) findViewById(R.id.productName);
        TextView weight = (TextView) findViewById(R.id.weightProduct);
        TextView conditionProduct = (TextView) findViewById(R.id.conditionProduct);
        TextView price = (TextView) findViewById(R.id.priceProduct);
        TextView discount = (TextView) findViewById(R.id.discountProduct);
        TextView category = (TextView) findViewById(R.id.categoryProduct);
        TextView shipmentPlans = (TextView) findViewById(R.id.shipmentPlanProduct);
        Button checkOut = (Button) findViewById(R.id.checkout_button);

        name.setText(ProductFragment.productClicked.name);
        weight.setText(String.valueOf(ProductFragment.productClicked.weight));
        if(ProductFragment.productClicked.conditionUsed = false){
            conditionProduct.setText("Used");
        }
        else{
            conditionProduct.setText("New");
        }
        price.setText(String.valueOf(ProductFragment.productClicked.price));
        discount.setText(String.valueOf(ProductFragment.productClicked.discount));
        category.setText(String.valueOf(ProductFragment.productClicked.category));
        switch (ProductFragment.productClicked.shipmentPlans) {
            case 0:
                shipmentPlans.setText("INSTANT");
                break;
            case 1:
                shipmentPlans.setText("SAME DAY");
                break;
            case 2:
                shipmentPlans.setText("NEXT DAY");
                break;
            case 3:
                shipmentPlans.setText("REGULER");
                break;
            default:
                shipmentPlans.setText("KARGO");
                break;
        }

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}
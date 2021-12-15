package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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
        TextView shipmentPlans = (TextView) findViewById(R.id.shipmentPlans);

        name.setText(ProductFragment.productClicked.name);
        weight.setText(String.valueOf(ProductFragment.productClicked.weight));
        conditionProduct.setText(convertConditionUsed(ProductFragment.productClicked.conditionUsed));
        price.setText(String.valueOf(ProductFragment.productClicked.price));
        discount.setText(String.valueOf(ProductFragment.productClicked.discount));
        category.setText(String.valueOf(ProductFragment.productClicked.category));
        shipmentPlans.setText(convertShipmentPlans(ProductFragment.productClicked.shipmentPlans));
    }

    private String convertShipmentPlans(byte shipment){
        switch (shipment) {
            case 0:
                return "INSTANT";
            case 1:
                return "SAME DAY";
            case 2:
                return "NEXT DAY";
            case 3:
                return "REGULER";
            default:
                return "CARGO";
        }
    }

    private String convertConditionUsed(boolean conditionUsed){
        if (conditionUsed) {
            return "Used";
        }else{
            return "New";
        }
    }
}
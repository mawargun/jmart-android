package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.model.Payment;
import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request.PaymentRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class PaymentActivity here
 * class for the purchase page of the product that the user wants to buy
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 *
 */

public class PaymentActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Payment paid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView name = (TextView) findViewById(R.id.nameProductPayment);
        TextView shipmentPlans = (TextView) findViewById(R.id.shipmentPlansPayment);
        TextView price = (TextView) findViewById(R.id.priceProductPayment);
        EditText productCount = (EditText) findViewById(R.id.payment_product_count);
        EditText address = (EditText) findViewById(R.id.address_payment);
        Button buy = (Button) findViewById(R.id.buy_payment);
        name.setText(ProductFragment.productClicked.name);
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
        price.setText(String.valueOf(ProductFragment.productClicked.price));
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                paid = gson.fromJson(object.toString(), Payment.class);
                                System.out.println(paid);
                                Toast.makeText(PaymentActivity.this,"product buyed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                                LoginActivity.loggedAccount.balance -= (ProductFragment.productClicked.price - (ProductFragment.productClicked.price*(ProductFragment.productClicked.discount/100)))*Double.parseDouble(productCount.getText().toString());
                                startActivity(intent);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(PaymentActivity.this,"failed to buy", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                PaymentRequest paymentRequest = new PaymentRequest(productCount.getText().toString(),address.getText().toString(),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
                requestQueue.add(paymentRequest);
            }
        });
    }
}
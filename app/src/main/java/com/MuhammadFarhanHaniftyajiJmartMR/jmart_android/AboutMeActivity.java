package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request.TopUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request.RegisterStoreRequest;

import org.json.JSONObject;

/**
 * Class AboutMeActivity here
 * displays information such as currently logged in accounts, top ups,
 * registers stores, and displays existing stores
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 */

public class AboutMeActivity extends AppCompatActivity {
    private TextView nameInfoDetail;
    private TextView emailInfoDetail;
    private TextView balanceInfoDetail;
    private TextView nameInfoStore;
    private TextView addressInfoStore;
    private TextView phoneInfoStore;
    private Button buttonTopUp;
    private EditText topUpInput, registerStoreName, registerStoreAddress, registerStorePhone;
    private CardView cardViewStoreInfo;
    private CardView cardViewRegisterStore;
    private Button buttonRegisterStoreCV, buttonCancelRegisterStore, buttonRegisterStore, buttonLogOut;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        RequestQueue queue = Volley.newRequestQueue(this);

        //digunakan untuk menghubungkan XML dengan AboutMeActivity.java
        nameInfoDetail = findViewById(R.id.tv_userName);
        emailInfoDetail = findViewById(R.id.tv_userEmail);
        balanceInfoDetail = findViewById(R.id.tv_userBalance);
        topUpInput = findViewById(R.id.et_topUpAmount);
        nameInfoDetail.setText(LoginActivity.getLoggedAccount().name);
        emailInfoDetail.setText(LoginActivity.getLoggedAccount().email);
        balanceInfoDetail.setText(String.valueOf("Rp." + LoginActivity.getLoggedAccount().balance));

        cardViewRegisterStore = findViewById(R.id.cv_registerStore);
        cardViewStoreInfo = findViewById(R.id.cv_storeExists);

        buttonRegisterStoreCV = findViewById(R.id.btnRegisterStore);
        buttonCancelRegisterStore = findViewById(R.id.btnRegisterStoreCancel);

        //button top up akan menambah balance jika ada klik pada button ini
        buttonTopUp = findViewById(R.id.btnTopUp);
        buttonTopUp.setOnClickListener(new View.OnClickListener() {

            /**
             * @description
             * button topup wether user input balance
             * then store it in its account
             */
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Boolean.parseBoolean(response)) {
                            Toast.makeText(AboutMeActivity.this, "Topup berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AboutMeActivity.this, "Topup error!", Toast.LENGTH_SHORT).show();
                        }
                        LoginActivity.loggedAccount.balance += Double.parseDouble(topUpInput.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                };
                TopUpRequest topUpRequest = new TopUpRequest(LoginActivity.getLoggedAccount().id, Double.parseDouble(topUpInput.getText().toString()), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(topUpRequest);
            }
        });

        //Jika akun tidak memiliki toko, maka ada tombol register store dan akan ada pop up jika ditekan
        //Jika akun sudah memiliki toko,maka akan ada detail toko dari akun tersebut
        if (LoginActivity.getLoggedAccount().store != null) {
            buttonRegisterStoreCV.setVisibility(View.GONE);
            cardViewStoreInfo.setVisibility(View.VISIBLE);
            nameInfoStore = findViewById(R.id.tv_storeNameF);
            addressInfoStore = findViewById(R.id.tv_storeAddressF);
            phoneInfoStore = findViewById(R.id.tv_storePhoneNumberF);
            nameInfoStore.setText(LoginActivity.getLoggedAccount().store.name);
            addressInfoStore.setText(LoginActivity.getLoggedAccount().store.address);
            phoneInfoStore.setText(LoginActivity.getLoggedAccount().store.phoneNumber);
        }
        buttonRegisterStoreCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegisterStoreCV.setVisibility(View.INVISIBLE);
                cardViewRegisterStore.setVisibility(View.VISIBLE);
                buttonLogOut = findViewById(R.id.logoutButton);
                buttonLogOut.setVisibility(View.GONE);
            }
        });

        buttonCancelRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegisterStoreCV.setVisibility(View.VISIBLE);
                cardViewRegisterStore.setVisibility(View.INVISIBLE);
                buttonLogOut = findViewById(R.id.logoutButton);
                buttonLogOut.setVisibility(View.VISIBLE);
            }
        });
        buttonRegisterStore = findViewById(R.id.btnRegisterStoreConfirm);
        registerStoreName = findViewById(R.id.et_storeName);
        registerStoreAddress = findViewById(R.id.et_storeAddress);
        registerStorePhone = findViewById(R.id.et_storePhoneNumber);

        //tombol untuk melakukan register store
        buttonRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerStoreName.getText().toString();
                String address = registerStoreAddress.getText().toString();
                String phoneNumber = registerStorePhone.getText().toString();
                if(!name.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty() ) {
                    RegisterStoreRequest registerStoreRequest = new RegisterStoreRequest(LoginActivity.getLoggedAccount().id, name, address, phoneNumber, new Response.Listener<String>() {

                        //beberapa pesan yang akan muncul tergantung kondisi saat berhasil atau tidaknya register store
                        @Override
                        public void onResponse(String response) {
                            LoginActivity.insertLoggedAccountStore(response);
                            try {
                                JSONObject object = new JSONObject(response);
                                if(object != null) {
                                    Toast.makeText(getApplicationContext(), "Register Store successful", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), AboutMeActivity.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Register Store unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Register Store unsuccessful, error occurred banget", Toast.LENGTH_LONG).show();
                        }
                    });
                    queue.add(registerStoreRequest);
                }

            }
        });

        /**
         * @description
         * button logout to go back to login activity
         */
        buttonLogOut = findViewById(R.id.logoutButton);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutMeActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
                finish();
            }
        });
    }
}
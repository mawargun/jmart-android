package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.model.Product;
import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Class FilterFragment here
 * Fragment filter serves to display the filter section
 * which can filter the list of desired products according to the parameters .
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 *
 */

public class FilterFragment extends Fragment {

    private static final Gson gson = new Gson();
    public static ArrayList<Product> productsList = new ArrayList<>();
    final int pageSize = 100;
    static int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //menginisiasi komponen yang ada pada fragment
        View productView = inflater.inflate(R.layout.fragment_filter, container, false);
        EditText name = productView.findViewById(R.id.nameInputFilter);
        EditText lowestPrice = productView.findViewById(R.id.lowestInputFilter);
        EditText highestPrice = productView.findViewById(R.id.highestInputFilter);
        CheckBox newCheck = productView.findViewById(R.id.newCheckFilter);
        CheckBox usedCheck = productView.findViewById(R.id.usedCheckFilter);
        Spinner category = productView.findViewById(R.id.SpinnerCategory);
        Button apply = productView.findViewById(R.id.applyButton);
        Button clear = productView.findViewById(R.id.clearButton);
        ListView lv = (ListView) productView.findViewById(R.id.filteredView);

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

        //mengosongkan parameter yang digunakan untuk menfilter
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                lowestPrice.setText("");
                highestPrice.setText("");
                newCheck.setChecked(false);
                usedCheck.setChecked(false);
                lv.setVisibility(View.GONE);
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                        }.getType());
                        System.out.println(productsList);
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );
                        lv.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));

        //menampilkan product yang berhasil melalui filter
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            if (object != null) {
                                productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {
                                }.getType());
                                System.out.println(productsList);
                                ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(
                                        getActivity(),
                                        android.R.layout.simple_list_item_1,
                                        productsList
                                );
                                lv.setVisibility(View.VISIBLE);
                                lv.setAdapter(listViewAdapter);

                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        ProductFragment.productClicked = (Product) lv.getItemAtPosition(i);
                                        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(RequestFactory.getProduct(page, pageSize, name.getText().toString(), lowestPrice.getText().toString(), highestPrice.getText().toString(), category.getSelectedItem().toString(), String.valueOf(newCheck.isChecked()), listener, null));
            }
        });
        return productView;
    }
}
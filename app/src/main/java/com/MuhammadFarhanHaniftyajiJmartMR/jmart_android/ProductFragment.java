package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.model.Product;
import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request.RequestFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Class ProductFragment here
 * Fragment filter displays a list of products registered on the back-end to main activity
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 *
 */

public class ProductFragment extends Fragment {

    private static final Gson gson = new Gson();
    public static ArrayList<Product> productsList = new ArrayList<>();
    final int pageSize = 21;
    static int page = 0;
    public static Product productClicked=null;
    public static ArrayAdapter<Product> listViewAdapter;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_product, container, false);
        EditText inputPage = productView.findViewById(R.id.InputPageProduct);
        Button nextButton = productView.findViewById(R.id.ButtonNext);
        Button prevButton = productView.findViewById(R.id.ButtonPrev);
        Button goButton = productView.findViewById(R.id.ButtonGo);

        inputPage.setText(String.valueOf(page + 1), TextView.BufferType.EDITABLE);

        /**
         * @description
         * nextButton is used to display the list of products on the next page
         */

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Next Page", Toast.LENGTH_SHORT).show();
                page += 1;
                getActivity().finish();
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        /**
         * @description
         * prevButton is used to display the list of products on the previous page
         */
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Prev Page", Toast.LENGTH_SHORT).show();
                page -= 1;
                if(page < 0){
                    page = 0;
                }
                getActivity().finish();
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        /**
         * @description
         * goButton is used to display a list of products on a particular page
         */

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Go!", Toast.LENGTH_SHORT).show();
                page = Integer.parseInt(inputPage.getText().toString()) - 1;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
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
                        listViewAdapter = new ArrayAdapter<Product>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                productsList
                        );
                        ListView lv = (ListView) productView.findViewById(R.id.ProductListView);

                        lv.setAdapter(listViewAdapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //productClicked = gson.fromJson(lv.getItemAtPosition(i).toString(),Product.class);
                                productClicked = (Product) lv.getItemAtPosition(i);
                                Toast.makeText(getActivity(),"product selected", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));

        return productView;
    }
}
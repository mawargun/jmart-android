package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.request;

import androidx.annotation.Nullable;

import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.LoginActivity;
import com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.ProductFragment;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class PaymentRequest here
 * Creating payment request about payment activity and connect it with back-end so user can pay and save in back-end
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 */

public class PaymentRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/payment/create";
    public final Map<String,String> params;

    public PaymentRequest(String productCount, String shipmentAddress, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId",String.valueOf(LoginActivity.loggedAccount.id));
        params.put("productId",String.valueOf(ProductFragment.productClicked.id));
        params.put("productCount",productCount);
        params.put("shipmentAddress",shipmentAddress);
        params.put("shipmentPlan",String.valueOf(ProductFragment.productClicked.shipmentPlans));
    }

    public Map<String,String> getParams(){
        return params;
    }
}
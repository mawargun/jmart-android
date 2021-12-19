package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.model;

/**
 * Class Product here
 * model as a Product similar to backend
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 */

public class Product extends Serializable
{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    public String toString() {
        return this.name;
    }
}

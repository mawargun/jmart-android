package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class Payment here
 * model as a Payment similar to backend
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 */

public class Payment extends Invoice{
    public Shipment shipment;
    public int productCount;
    public ArrayList<Record> history = new ArrayList<Record>();

    public  static class Record {
        public final Date date;
        public String massage;
        public Status status;

        public Record( Status status, String massage) {
            this.date = java.util.Calendar.getInstance().getTime();
            this.status = status;
            this.massage = massage;
        }

    }
}

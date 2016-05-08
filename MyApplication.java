package com.stock_search.arjun.stockmarketviewer;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Arjun on 03-05-2016.
 */
public class MyApplication extends Application {
    public ArrayList<String> favorites =new ArrayList<String>(10);
}

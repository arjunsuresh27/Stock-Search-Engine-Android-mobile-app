package com.stock_search.arjun.stockmarketviewer;

import static com.stock_search.arjun.stockmarketviewer.Constants.FIRST_COLUMN;
import static com.stock_search.arjun.stockmarketviewer.Constants.SECOND_COLUMN;
import static com.stock_search.arjun.stockmarketviewer.Constants.THIRD_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Created by Arjun on 04-05-2016.
 */
public class ListViewAdapters extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;


    public ListViewAdapters(Activity activity,ArrayList<HashMap<String, String>> list){

        this.activity=activity;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.c1);
            txtSecond=(TextView) convertView.findViewById(R.id.c2);
            txtThird=(TextView) convertView.findViewById(R.id.c3);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));

        return convertView;
    }




}

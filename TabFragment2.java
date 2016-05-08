package com.stock_search.arjun.stockmarketviewer;

/**
 * Created by Arjun on 20-04-2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class TabFragment2 extends Fragment {

    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(true);


        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);

        //code to obtain the Symbol to pass it to the URL

        ResultActivity activity = (ResultActivity) getActivity();
        String myDataFromActivity = activity.gettab1data();
        Log.d("gettu:", myDataFromActivity);

        if (myDataFromActivity != "Empty") {

            try {
                JSONObject reader = new JSONObject(myDataFromActivity);
                String cSymbol = reader.getString("Symbol");// got the symbol to pass it to the URL
                Log.d("vj",cSymbol);

                WebView webView = (WebView) v.findViewById(R.id.webView);
                webView.setWebViewClient(new MyWebViewClient());

                String url = "http://cs-server.usc.edu:11082/index2.php?hist=" + cSymbol;
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(url);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return v;

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }
}
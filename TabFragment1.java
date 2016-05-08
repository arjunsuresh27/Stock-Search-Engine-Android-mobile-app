package com.stock_search.arjun.stockmarketviewer;

/**
 * Created by Arjun on 20-04-2016.
 */
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class TabFragment1 extends Fragment {

    private ImageView mDialog;
    ImageView mImageView;
    PhotoViewAttacher mAttacher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Reached gettu","gettu");
        //Log.d("ans is",ans);
        //obtain the  Argumets

        // obtain the string from gettab1data() method in Result Activity
        ResultActivity activity = (ResultActivity) getActivity();
        String myDataFromActivity = activity.gettab1data();
        Log.d("gettu:",myDataFromActivity);

        View v=inflater.inflate(R.layout.tab_fragment_1, container, false);
        final ImageView iV = (ImageView) v.findViewById(R.id.yahoo);

        TextView tv1= (TextView)v.findViewById(R.id.label1);
        TextView tv2= (TextView)v.findViewById(R.id.label2);
        TextView tv3= (TextView)v.findViewById(R.id.label3);
        TextView tv4= (TextView)v.findViewById(R.id.label4);
        TextView tv5= (TextView)v.findViewById(R.id.label5);
        TextView tv6= (TextView)v.findViewById(R.id.label6);
        TextView tv7= (TextView)v.findViewById(R.id.label7);
        TextView tv8= (TextView)v.findViewById(R.id.label8);
        TextView tv9= (TextView)v.findViewById(R.id.label9);
        TextView tv10= (TextView)v.findViewById(R.id.label10);
        TextView tv11= (TextView)v.findViewById(R.id.label11);

        //decoding the JSON string
        try {
            if(myDataFromActivity!="Empty")
            {
                JSONObject reader= new JSONObject(myDataFromActivity);
                String cName = reader.getString("Name");
                // Get the value of key "cSymbol" under JSONObject "lkpobj"
                tv1.setText(cName);
                String cSymbol = reader.getString("Symbol");
                tv2.setText(cSymbol);

                String cLP = reader.getString("LastPrice");
                tv3.setText(cLP);

                String cCh = reader.getString("Change");
                //roudning to 2 decimal places
                double iCh =Double.parseDouble(cCh);
                iCh =(double)Math.round(iCh*100)/100;

                String cCp = reader.getString("ChangePercent");
                //round to 2 decimal places
                double iCp = Double.parseDouble(cCp);
                iCp=(double)Math.round(iCp*100)/100;

                SpannableStringBuilder builder = new SpannableStringBuilder();
                if(iCh>0)
                {
                    builder.append(String.valueOf(iCh)).append("(").append(String.valueOf(iCp)).append(")").append(" ", new ImageSpan(getActivity(),R.mipmap.up),0) ;
                }

                else
                {
                    builder.append(String.valueOf(iCh)).append("(").append(String.valueOf(iCp)).append(")").append(" ", new ImageSpan(getActivity(),R.mipmap.down),0) ;
                }

                tv4.setText(builder);


                String cTs = reader.getString("Timestamp");
                Log.d("cTs",cTs);
                if(cTs!="null")
                {
                    String[] date=cTs.split(" ");
                    String fin_date=date[2]+" "+date[1]+" "+date[5]+", "+date[3];
                    tv5.setText(fin_date);
                }

                else
                {
                    tv5.setText("NA");
                }

                String cMc =reader.getString("MarketCap");
                double iMc =Double.parseDouble(cMc);
                if(iMc>1000000000)
                {
                    iMc =iMc/1000000000;
                    iMc=(double)Math.round(iMc*100)/100;
                    cMc=String.valueOf(iMc)+" Billion";
                }

                else if(iMc>1000000)
                {
                    iMc =iMc/1000000;
                    iMc=(double)Math.round(iMc*100)/100;
                    cMc=String.valueOf(iMc)+" Million";

                }

                tv6.setText(cMc);


                String cVl =reader.getString("Volume");
                tv7.setText(cVl);

                String cCy =reader.getString("ChangeYTD");
                //roudning to 2 decimal places
                double iCy =Double.parseDouble(cCy);
                iCy =(double)Math.round(iCy*100)/100;

                String cCyp =reader.getString("ChangePercentYTD");
                //round to 2 decimal places
                double iCyp = Double.parseDouble(cCyp);
                iCyp=(double)Math.round(iCyp*100)/100;
                SpannableStringBuilder builder1 = new SpannableStringBuilder();
                if(iCy>0)
                {
                    builder1.append(String.valueOf(iCy)).append("(").append(String.valueOf(iCyp)).append(")").append(" ", new ImageSpan(getActivity(),R.mipmap.up),0) ;
                }

                else
                {
                    builder1.append(String.valueOf(iCy)).append("(").append(String.valueOf(iCyp)).append(")").append(" ", new ImageSpan(getActivity(),R.mipmap.down),0) ;
                }

                tv8.setText(builder1);

                String cHi =reader.getString("High");
                tv9.setText(cHi);

                String cLo =reader.getString("Low");
                tv10.setText(cLo);
                String cOp = reader.getString("Open");
                tv11.setText(cOp);

                //tv.setText(tv.getText()+ "\n"+ cName + "\n");
                //code to add an empty page divider
                View line = new View(getActivity());
                line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                line.setBackgroundColor(Color.rgb(51, 51, 51));
                ;


                //row divider ends here



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //String stream=getArguments().getString("stream");
        //Log.d("gettu:",stream);

        //Call to the Async Task to load the Image


        JSONObject reader = null;
        try {
            reader = new JSONObject(myDataFromActivity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Symbol = null;
        try {
            Symbol = reader.getString("Symbol");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Calling the Image View Code
        String Yahoo_URL="http://chart.finance.yahoo.com/t?s="+Symbol+"&lang=en-US&width=1080&height=480";
        new DownloadImageTask((ImageView) v.findViewById(R.id.yahoo)).execute(Yahoo_URL);



        iV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Working Code Just enable this
                Dialog settingsDialog = new Dialog(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View newView = (View) inflater.inflate(R.layout.image_layout, null);

                settingsDialog.setContentView(newView);
               // settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                ImageView iv= (ImageView) newView.findViewById(R.id.yahoo);
                Bitmap bm=((BitmapDrawable)iV.getDrawable()).getBitmap();
                iv.setImageBitmap(bm);
                settingsDialog.show();

                //code for double click
                End of Working code here */

                android.app.FragmentManager fm = getActivity().getFragmentManager();

                MyDialogFragment dialogFragment = new MyDialogFragment ();
                dialogFragment.show(fm, "Sample Fragment");

            }




        }




















        );


        return v;       //This needs to be at the end only



    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("her","I'm here now");


    }

    //Code for the Image View to Obtain it using HTTP

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);



        }
    }










}